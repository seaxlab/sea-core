package com.github.spy.sea.core.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/22
 * @since 1.0
 */
@Slf4j
public abstract class AbstractCoreSpringTest extends AbstractCoreTest {

    protected ClassPathXmlApplicationContext ctx;

    @Before
    public void before() {
        String xmlConfigFile = getXmlConfigFile();
        ctx = new ClassPathXmlApplicationContext(new String[]{xmlConfigFile});
        ctx.start();
        log.info("spring container started.");
    }

    protected abstract String getXmlConfigFile();

    @After
    public void after() {
        if (ctx != null) {
            ctx.close();
        }
    }
}
