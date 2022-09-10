package com.github.seaxlab.core.lang.sql;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.lang.sql.util.TimeUtil;
import com.github.seaxlab.core.util.DateUtil;
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

        log.info("{}", DateUtil.toString(time, DateFormatEnum.DEFAULT));
        log.info("{}", DateUtil.toString(time, DateFormatEnum.HH_mm_ss));
    }
}
