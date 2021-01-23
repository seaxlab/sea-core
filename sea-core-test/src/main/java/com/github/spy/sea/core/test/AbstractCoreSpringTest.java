package com.github.spy.sea.core.test;

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

    // 这种方式也不错
//    @ContextConfiguration("classpath:spring/*.xml")
//    @ContextConfiguration(classes = AbstractCoreSpringTest.class)

    @Before
    public void setUp() throws Exception {
        log.info("spring container ready!");
    }

    @Test
    public void test() {
        log.info("ctx={}", ctx);
        log.info("bean={}", ctx.getBean("helloWorld"));
    }

// manual.
//    @Before
//    public void before() {
//        String xmlConfigFile = getXmlConfigFile();
//        ctx = new ClassPathXmlApplicationContext(new String[]{xmlConfigFile});
//        ctx.start();
//        log.info("spring container started.");
//    }
//
//    protected abstract String getXmlConfigFile();
//
//    @After
//    public void after() {
//        if (ctx != null) {
//            ctx.close();
//        }
//    }

}
