package com.github.spy.sea.core.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/24
 * @since 1.0
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
// classes 使用测试类定义的
//@SpringBootTest(classes = SpringBootWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application.yml")
public class AbstractCoreSpringBootTest extends AbstractCoreTest {

    @Autowired
    protected AbstractApplicationContext ctx;

    private void usage() {
        log.info("@SpringBootTest(classes = SpringBootWebApplication.class)");
    }
}
