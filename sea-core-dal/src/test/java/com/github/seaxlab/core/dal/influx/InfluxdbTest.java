package com.github.seaxlab.core.dal.influx;

import com.github.seaxlab.core.dal.BaseCoreDalTest;
import com.github.seaxlab.core.util.DateUtil;
import com.github.seaxlab.core.util.MessageUtil;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/17
 * @since 1.0
 */
public class InfluxdbTest extends BaseCoreDalTest {

    private Logger log = LoggerFactory.getLogger(InfluxdbTest.class);
    private String org;
    private String bucket;
    private InfluxDBClient influxDBClient;

    @Before
    public void before() {
        String url = "http://n9e001:8086";
        char[] token = "4p9kw-uyD_wcYnGSoieGBdvZCbp63hkEZ5qapvRvtToWdXK1AYuDxlP7gSXaT4EQ887F1OGuNPdUgxwVEpH-tQ==".toCharArray();
        org = "yuantu";
        bucket = "sea-stat2";
        influxDBClient = InfluxDBClientFactory.create(url, token, org);
    }

    @Test
    public void addTest() throws Exception {

        try (WriteApi api = influxDBClient.getWriteApi()) {

            Point point = Point.measurement("my-abc")
                               .time(System.currentTimeMillis(), WritePrecision.MS)
                               .addTag("env", "pro")
                               .addField("value", "1");

            point.time(System.nanoTime(), WritePrecision.NS);

            api.writePoint("bucket-test", org, point);
            api.flush();
        }
        sleepMinute(1);
    }


    @Test
    public void todayTotalTest() throws Exception {

        Date[] dates = DateUtil.getBeginAndEndDateTimeOfDay(new Date());
        Instant start = dates[0].toInstant();
        Instant stop = dates[1].toInstant();

        totalStat(start, stop, SORT_VALUE_DESC);
    }

    @Test
    public void yesterdayTotalTest() throws Exception {
        Date[] dates = DateUtil.getBeginAndEndDateTimeOfDay(DateUtil.addDay(new Date(), -1));
        Instant start = dates[0].toInstant();
        Instant stop = dates[1].toInstant();

        totalStat(start, stop, SORT_VALUE_DESC);
    }

    private void totalStat(Instant start, Instant stop, Comparator<ItemVO> comparator) {
        Restrictions restriction = Restrictions.and(
                Restrictions.tag("region").equal("qingdao_pro")
        );

        String[] group = {"app", "_measurement"};
        Flux flux = Flux.from(bucket)
                        .range(start, stop)
                        .filter(restriction)
                        .groupBy(group)
                        .sum();

        String sql = flux.toString();
        log.info("flux SQL is\n{}", sql);
        List<FluxTable> list = influxDBClient.getQueryApi().query(sql);

        log.info("{}", list.size());

        List<ItemVO> items = new ArrayList<>();
        list.stream().forEach(item -> {
            item.getRecords().forEach(record -> {
                ItemVO vo = new ItemVO();
                vo.setMetric(record.getMeasurement());
                vo.setValue((Double) (record.getValue()));
                items.add(vo);
            });
        });

        // 排序
        if (comparator != null) {
            Collections.sort(items, comparator);
        }

        log.info("after sort");
        items.forEach(item -> {
            String msg = MessageUtil.format("{},{}", item.metric, item.value);
            System.out.println(msg);
        });
    }

    public static Comparator<ItemVO> SORT_VALUE_ASC = (o1, o2) -> Double.compare(o1.value, o2.value);
    public static Comparator<ItemVO> SORT_VALUE_DESC = (o1, o2) -> Double.compare(o2.value, o1.value);
    public static Comparator<ItemVO> SORT_NAME_ASC = (o1, o2) -> o1.metric.compareTo(o2.metric);
    public static Comparator<ItemVO> SORT_NAME_DESC = (o1, o2) -> o2.metric.compareTo(o1.metric);


    @Data
    private class ItemVO implements Comparable<ItemVO> {

        private String metric;
        private Double value;

        @Override
        public int compareTo(@NotNull ItemVO o) {
            return Double.compare(this.value, o.value);
        }
    }

    /**
     * 单表聚合曲线sql
     *
     * @throws Exception
     */
    @Test
    public void buildAggrSum() throws Exception {
        // 时间
        Date[] dates = DateUtil.getBeginAndEndDateTimeOfDay(new Date());
        Instant start = dates[0].toInstant();
        Instant stop = dates[1].toInstant();

        // tag过滤
        Restrictions restriction = Restrictions.and(
                Restrictions.measurement().equal("/plancenter/rest/api/schList"),
                Restrictions.tag("region").equal("qingdao_pro"),
                Restrictions.tag("app").equal("plancenter")
        );

        // 分组
        String[] group = {"_measurement"};

        Flux flux = Flux.from(bucket)
                        .range(start, stop)
                        .filter(restriction)
                        .groupBy(group)
                        .aggregateWindow(1L, ChronoUnit.MINUTES, "sum");


        log.info("flux sql is \n{}", flux.toString());
    }


    @Test
    public void testInflux() throws Exception {


        Restrictions restriction = Restrictions.and(
//                Restrictions.measurement().equal("/plancenter/admin/depts/list/json"),
                Restrictions.tag("region").equal("qingdao_pro")
//                Restrictions.tag("instance_type").equal(Pattern.compile("/prod/")),
//                Restrictions.field().greater(10.5D),
//                Restrictions.time().lessOrEqual(new TimeInterval(-15L, ChronoUnit.MINUTES))
        );

        String[] group = {"app", "_measurement"};

        Date[] dates = DateUtil.getBeginAndEndDateTimeOfDay(new Date());
        Instant start = dates[0].toInstant();
        Instant stop = dates[1].toInstant();

        Flux flux = Flux.from(bucket)
                        .range(start, stop)
                        .filter(restriction)
                        .groupBy(group);


        log.info("flux max\n{}", flux.max().toString());
        List<FluxTable> list = influxDBClient.getQueryApi().query(flux.sum().toString());

        list.stream().forEach(item -> {
            item.getRecords().forEach(record -> {
                Map<String, Object> map = record.getValues();
                log.info("map={}", map);
//                log.info("measurement={},value={}", record.getMeasurement(), record.getValue());
            });
        });
    }

}
