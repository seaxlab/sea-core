package com.github.spy.sea.core.sql;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.sql.util.TimeUtil;
import com.github.spy.sea.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/26
 * @since 1.0
 */
@Slf4j
public class TimeUtilTest extends BaseCoreTest {

    @Test
    public void test18() throws Exception {
        Date date = new Date();
        Time time = TimeUtil.of(date);

        log.info("{}", DateUtil.toYMDHMS(time));
        log.info("{}", DateUtil.toHMS(time));
    }
}
