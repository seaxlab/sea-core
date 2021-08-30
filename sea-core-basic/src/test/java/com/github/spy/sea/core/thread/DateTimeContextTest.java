package com.github.spy.sea.core.thread;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.SimpleDateFormat;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/30
 * @since 1.0
 */
@Slf4j
public class DateTimeContextTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        SimpleDateFormat sdf = DateTimeContext.get(DateUtil.DEFAULT_FORMAT);
        SimpleDateFormat sdf1 = DateTimeContext.get(DateUtil.DEFAULT_FORMAT);
        SimpleDateFormat sdf2 = DateTimeContext.get(DateUtil.DEFAULT_FORMAT);

        log.info("sdf={}", sdf);
        log.info("sdf=sdf1? {}", sdf == sdf1);
        log.info("sdf=sdf2? {}", sdf == sdf2);

    }
}
