package com.github.spy.sea.core.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-26
 * @since 1.0
 */
@Slf4j
public class ConfigurationTest {

    Configuration config;

    @Before
    public void before() {
        System.setProperty("sea.config.xx", "xx");
        config = ConfigurationFactory.getInstance();

    }

    @Test
    public void run16() throws Exception {

        println("1");
        println("user.home");
        println("sea.env");
        println("sea.config.env");
        println("sea.config.title");
        println("sea.config.xx");


    }

    private void println(String path) {
        log.info("{}={}", path, config.getString(path));
    }
}
