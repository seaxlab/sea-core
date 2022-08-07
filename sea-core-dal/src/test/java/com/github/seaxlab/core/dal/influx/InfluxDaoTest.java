package com.github.seaxlab.core.dal.influx;

import com.github.seaxlab.core.dal.BaseCoreDalTest;
import com.github.seaxlab.core.dal.influx.dao.InfluxDao;
import com.github.seaxlab.core.dal.influx.dao.impl.DefaultInfluxDao;
import com.github.seaxlab.core.dal.influx.model.BatchAddDTO;
import com.github.seaxlab.core.dal.influx.model.QueryDTO;
import com.github.seaxlab.core.dal.influx.model.Row;
import com.github.seaxlab.core.util.DateUtil;
import com.github.seaxlab.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/18
 * @since 1.0
 */
@Slf4j
public class InfluxDaoTest extends BaseCoreDalTest {

    String bucket;

    private InfluxDao influxDao;

    @Before
    public void before() throws Exception {
        String url = "http://n9e001:8086";
        String token = "4p9kw-uyD_wcYnGSoieGBdvZCbp63hkEZ5qapvRvtToWdXK1AYuDxlP7gSXaT4EQ887F1OGuNPdUgxwVEpH-tQ==";
        String org = "";
        bucket = "sea-stat2";
        influxDao = new DefaultInfluxDao(url, token, org);
    }

    /**
     * 今日数据统计
     *
     * @throws Exception
     */
    @Test
    public void queryTodaySumTest() throws Exception {
        Date[] dates = DateUtil.getBeginAndEndDateTimeOfDay(DateUtil.nowDate());
        Date start = dates[0];
        Date stop = dates[1];

        basicSum(start, stop, InfluxDao.SORT_VALUE_DESC);
    }

    /**
     * 昨日数据统计
     *
     * @throws Exception
     */
    @Test
    public void queryYesterdaySumTest() throws Exception {
        Date[] dates = DateUtil.getBeginAndEndDateTimeOfDay(DateUtil.addDay(new Date(), -1));
        Date start = dates[0];
        Date stop = dates[1];

        basicSum(start, stop, InfluxDao.SORT_VALUE_DESC);
    }

    /**
     * 今日相关性统计
     *
     * @throws Exception
     */
    @Test
    public void queryTodaySumRelationTest() throws Exception {
        Date[] dates = DateUtil.getBeginAndEndDateTimeOfDay(DateUtil.nowDate());
        Date start = dates[0];
        Date stop = dates[1];

        basicSum(start, stop, InfluxDao.SORT_NAME_ASC);
    }

    /**
     * 昨日相关性统计
     *
     * @throws Exception
     */
    @Test
    public void queryYesterdaySumRelationTest() throws Exception {
        Date[] dates = DateUtil.getBeginAndEndDateTimeOfDay(DateUtil.addDay(new Date(), -1));
        Date start = dates[0];
        Date stop = dates[1];

        basicSum(start, stop, InfluxDao.SORT_NAME_ASC);
    }

    @Test
    public void batchInsertTest() throws Exception {

        BatchAddDTO dto = new BatchAddDTO();
        dto.setBucket("bucket-test");

        Map<String, Object> tags = new HashMap<>();
        tags.put("env", "pro");
        tags.put("region", "hangzhou");

        List<Row> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Row row = new Row();
            row.setMetric("my-test2");
            row.setValue(i);
            row.setTags(tags);
            data.add(row);
        }
        dto.setData(data);

        boolean ret = influxDao.batchInsert(dto);
        log.info("ret={}", ret);

    }


    private void basicSum(Date start, Date stop, Comparator comparator) {
        QueryDTO dto = new QueryDTO();
        dto.setBucket(bucket);
        dto.setStart(start);
        dto.setStop(stop);

        // filter
        Map<String, Object> tags = new HashMap<>();
        tags.put("region", "qingdao_pro");
        dto.setTags(tags);

        //group by
        List<String> groupBys = new ArrayList<>();
        groupBys.add("app");
        groupBys.add("_measurement");
        dto.setGroupBy(groupBys);

        //sort
        dto.setComparator(comparator);

        List<Row> records = influxDao.querySum(dto);

        records.stream().forEach(item -> println2(item.getMetric(), item.getValue()));
    }


    private void println2(String v1, Object v2) {
        String msg = MessageUtil.format("{},{}", v1, v2);
        System.out.println(msg);
    }

}
