package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-20
 * @since 1.0
 */
@Slf4j
public class DateUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        println(DateUtil.nowStr());
        println(DateUtil.nowDate());
        println(DateUtil.now("MM-dd*HH:mm"));

        Date now = DateUtil.nowDate();

        Date day1 = DateUtil.addDay(now, 1);

        println(DateUtil.addHour(now, 1));
        println(DateUtil.addMinute(now, 15));
    }
}
