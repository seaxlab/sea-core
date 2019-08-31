package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
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
    public void run17() throws Exception {

        Assert.assertEquals(-1, TimeUtil.compare("01:00", "02:00"));
        Assert.assertEquals(0, TimeUtil.compare("01:00", "01:00"));
        Assert.assertEquals(1, TimeUtil.compare("01:00", "00:20"));
    }

    @Test
    public void run27() throws Exception {
        String newTimeStr = TimeUtil.add("12:10", TimeUtil.FORMAT_HHmm, 20, TimeUnit.MINUTES);
        log.info("newTimeStr={}", newTimeStr);


        LocalTime time = LocalTime.now();
        LocalTime newTime = TimeUtil.add(time, 10, TimeUnit.MINUTES);
        log.info("newTime={}", newTime);

    }
}
