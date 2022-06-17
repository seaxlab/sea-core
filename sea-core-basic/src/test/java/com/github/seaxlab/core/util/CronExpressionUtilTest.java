package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/1
 * @since 1.0
 */
@Slf4j
public class CronExpressionUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        log.info("{}", CronExpressionUtil.isValid("12"));
        log.info("{}", CronExpressionUtil.isValid("0/20 * * * * ?"));

    }
}
