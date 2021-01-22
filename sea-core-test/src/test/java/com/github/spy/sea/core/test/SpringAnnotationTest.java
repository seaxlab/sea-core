package com.github.spy.sea.core.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/22
 * @since 1.0
 */
@Slf4j
@ContextConfiguration(classes = SpringAnnotationTest.class)
public class SpringAnnotationTest extends AbstractCoreSpringTest {

    @Test
    public void test17() throws Exception {
        log.info("ctx={}", ctx);
    }
}
