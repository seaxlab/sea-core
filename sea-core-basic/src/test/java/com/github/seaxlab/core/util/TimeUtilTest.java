package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.enums.RangeModeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public class TimeUtilTest extends BaseCoreTest {

    @Test
    public void testCompareTime() throws Exception {

        Assert.assertEquals(-1, TimeUtil.compare("01:00", "02:00"));
        Assert.assertEquals(0, TimeUtil.compare("01:00", "01:00"));
        Assert.assertEquals(1, TimeUtil.compare("01:00", "00:20"));
    }

    @Test
    public void testCompareDateTime() throws Exception {
        Date date1 = DateUtil.toDate("2021-04-01 13:01:50", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
        Date date2 = DateUtil.toDate("2021-03-01 14:01:50", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());

        Assert.assertEquals(TimeUtil.compare(date1, date2), -1);
    }


    @Test
    public void testNowIsInRange() throws Exception {

        Assert.assertTrue(TimeUtil.nowIsInRange("11:16", "12:00", DateFormatEnum.HH_mm.getValue()));
        Assert.assertFalse(TimeUtil.nowIsInRange("11:30", "12:00", DateFormatEnum.HH_mm.getValue()));
    }

    @Test
    public void testIsInRange() throws Exception {
        boolean is = TimeUtil.isInRange("11:0", "11:00", "23:00", DateFormatEnum.HH_mm.getValue(), RangeModeEnum.CLOSE_CLOSE);
        log.info("{}", is);
        SimpleDateFormat format = new SimpleDateFormat(DateFormatEnum.HH_mm.getValue());
        log.info("{}", format.parse("23:00"));
        log.info("{}", format.parse("00:00"));
    }

    @Test
    public void testAdd() throws Exception {
        String newTimeStr = TimeUtil.add("12:10", DateFormatEnum.HH_mm.getValue(), 20, TimeUnit.MINUTES);
        log.info("newTimeStr={}", newTimeStr);


        LocalTime time = LocalTime.now();
        LocalTime newTime = TimeUtil.add(time, 10, TimeUnit.MINUTES);
        log.info("newTime={}", newTime);
    }

    @Test
    public void testToTimeUnit() throws Exception {
        Assert.assertEquals(TimeUtil.toTimeUnit(10000), "10.00 μs");
    }

    @Test
    public void testOf1() throws Exception {
        Date date = TimeUtil.of(12, 10);
        log.info("date={}", date);

        Date date2 = TimeUtil.of(12, 10, 30);
        log.info("date2={}", date2);
    }


}
