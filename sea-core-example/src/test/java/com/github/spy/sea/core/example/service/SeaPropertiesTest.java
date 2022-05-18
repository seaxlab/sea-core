package com.github.spy.sea.core.example.service;

import com.github.spy.sea.core.boot.autoconfigure.SeaProperties;
import com.github.spy.sea.core.example.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/30
 * @since 1.0
 */
@Slf4j
public class SeaPropertiesTest extends BaseSpringTest {

    @Autowired
    SeaProperties seaProperties;

    @Test
    public void run17() throws Exception {
        log.info("properties={}", seaProperties.getProperties());
    }
}
