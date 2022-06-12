package com.github.seaxlab.core.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * spring base test
 *
 * @author spy
 * @version 1.0 2021/1/22
 * @since 1.0
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractCoreSpringTest extends AbstractCoreTest {

    @Autowired
    protected AbstractApplicationContext ctx;

    @Before
    public void setUp() throws Exception {
        log.info("spring container ready!");
    }

    @Test
    public void test() {
        log.info("ctx={}", ctx);
        log.info("bean={}", ctx.getBean("helloWorld"));
    }

    private void usage() {
        log.info("@ContextConfiguration(classpath:spring/*.xml)");
        log.info("or");
        log.info("@ContextConfiguration(classes = AbstractCoreSpringTest.class)");
    }

    private void startXmlApplication() {
        log.info("this is by manual");
        log.info("ctx = new ClassPathXmlApplicationContext(new String[]{xmlConfigFile});");
        log.info("ctx.start();");
    }

}
