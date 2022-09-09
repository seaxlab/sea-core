package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/10
 * @since 1.0
 */
@Slf4j
public class BirthdayUtilTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        Date birthday = DateUtil.toDate("19881201", "yyyyMMdd");
        log.info("age={}", BirthdayUtil.getAge(birthday));
        log.info("months={}", BirthdayUtil.getMonths(birthday));
        log.info("days={}", BirthdayUtil.getDays(birthday));
    }
}
