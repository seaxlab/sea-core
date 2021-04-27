package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.enums.RangeModeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static com.github.spy.sea.core.util.TimeUtil.FORMAT_HHmm;

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
    public void run17() throws Exception {

        Assert.assertEquals(-1, TimeUtil.compare("01:00", "02:00"));
        Assert.assertEquals(0, TimeUtil.compare("01:00", "01:00"));
        Assert.assertEquals(1, TimeUtil.compare("01:00", "00:20"));
    }


    @Test
    public void testNowIsInRange() throws Exception {

        Assert.assertTrue(TimeUtil.nowIsInRange("11:16", "12:00", FORMAT_HHmm));
        Assert.assertFalse(TimeUtil.nowIsInRange("11:30", "12:00", FORMAT_HHmm));
    }

    @Test
    public void testIsInRange() throws Exception {
        boolean is = TimeUtil.isInRange("11:0", "11:00", "23:00", FORMAT_HHmm, RangeModeEnum.CLOSE_CLOSE);
        log.info("{}", is);
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_HHmm);
        log.info("{}", format.parse("23:00"));
        log.info("{}", format.parse("00:00"));
    }

    @Test
    public void run27() throws Exception {
        String newTimeStr = TimeUtil.add("12:10", FORMAT_HHmm, 20, TimeUnit.MINUTES);
        log.info("newTimeStr={}", newTimeStr);


        LocalTime time = LocalTime.now();
        LocalTime newTime = TimeUtil.add(time, 10, TimeUnit.MINUTES);
        log.info("newTime={}", newTime);
    }

    @Test
    public void run42() throws Exception {
        Assert.assertEquals(TimeUtil.toTimeUnit(10000), "10.00 μs");
    }
}
