package com.github.seaxlab.core.dal.influx.dao.impl;

import com.github.seaxlab.core.dal.influx.dao.InfluxDao;
import com.github.seaxlab.core.dal.influx.model.BatchAddDTO;
import com.github.seaxlab.core.dal.influx.model.QueryDTO;
import com.github.seaxlab.core.dal.influx.model.Row;
import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.ListUtil;
import com.github.seaxlab.core.util.MapUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.influxdb.LogLevel;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.RangeFlux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/18
 * @since 1.0
 */
public class DefaultInfluxDao implements InfluxDao {

  private final Logger log = LoggerFactory.getLogger(DefaultInfluxDao.class);

  private final String org;

  private final String bucket;

  private final InfluxDBClient influxDBClient;

  public DefaultInfluxDao(String url, String token, String org) {
    this(url, token, org, null);
  }

  public DefaultInfluxDao(String url, String token, String org, String bucket) {
    influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
    this.org = org;
    this.bucket = bucket;

    influxDBClient.setLogLevel(LogLevel.BASIC);
  }


  @Override
  public boolean batchInsert(BatchAddDTO dto) {
    Preconditions.checkNotNull(dto);
    String finalOrg = StringUtil.defaultString(dto.getOrg(), org);
    Preconditions.checkNotNull(finalOrg);

    String finalBucket = StringUtil.defaultString(dto.getBucket(), bucket);
    Preconditions.checkNotNull(finalBucket);

    if (ListUtil.isEmpty(dto.getData())) {
      log.warn("data is null");
      return true;
    }
    log.info("batch add size={}", dto.getData().size());

    try (WriteApi api = influxDBClient.getWriteApi()) {

      List<Point> points = dto.getData().stream()
                              .map(item -> {
                                Point point = Point.measurement(item.getMetric());
                                point.addField("value", Double.valueOf(item.getValue().toString()));
                                if (item.getTags() != null) {
                                  Map<String, String> newTags = new HashMap<>();
                                  item.getTags().forEach((key, value) -> {
                                    newTags.put(key, value.toString());
                                  });
                                  point.addTags(newTags);
                                }
                                if (item.getTime() > 0) {
                                  point.time(item.getTime(), WritePrecision.MS);
                                } else {
                                  log.warn("time is null");
                                }

                                if (item.getFields() != null) {
                                  point.addFields(item.getFields());
                                }
                                return point;
                              }).collect(Collectors.toList());

      api.writePoints(finalBucket, finalOrg, points);
      api.flush();
    } catch (Exception e) {
      log.error("fail to write to influxdb", e);
      return false;
    }

    return true;
  }

  @Override
  public List<Row> querySum(QueryDTO dto) {

    Flux flux = Flux.from(dto.getBucket());
    RangeFlux rangeFlux = flux.range();
    if (dto.getStart() != null) {
      rangeFlux = rangeFlux.withStart(dto.getStart().toInstant());
    }
    if (dto.getStop() != null) {
      rangeFlux = rangeFlux.withStop(dto.getStop().toInstant());
    }

    Flux subFlux;
    if (MapUtil.isNotEmpty(dto.getTags())) {
      List<Restrictions> restrictions = new ArrayList<>();
      dto.getTags().forEach((key, value) -> {
        restrictions.add(Restrictions.tag(key).equal(value));
      });

      subFlux = rangeFlux.filter(Restrictions.and(ArrayUtil.toArray(restrictions, Restrictions.class)));
    } else {
      subFlux = rangeFlux;
    }
    if (ListUtil.isNotEmpty(dto.getGroupBy())) {
      subFlux = subFlux.groupBy(dto.getGroupBy());
    }

    subFlux = subFlux.sum();

    Flux finalFlux = subFlux;
    String sql = finalFlux.toString();
    log.info("flux SQL is\n{}", sql);

    Stopwatch stopwatch = Stopwatch.createStarted();
    List<FluxTable> list;
    try {
      list = influxDBClient.getQueryApi().query(sql);
    } finally {
      log.info("influx query cost={}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
    if (ListUtil.isEmpty(list)) {
      return ListUtil.empty();
    }
    log.info("record size={}", list.size());

    List<Row> items = new ArrayList<>();
    list.stream().forEach(item -> {
      item.getRecords().forEach(record -> {
        Row row = new Row();
        row.setMetric(record.getMeasurement());
        row.setValue(record.getValue());
        row.setTags(record.getValues());
        items.add(row);
      });
    });

    // 排序
    if (dto.getComparator() != null) {
      log.info("execute data sort.");
      Collections.sort(items, dto.getComparator());
    }

    return items;
  }
}
