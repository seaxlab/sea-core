package com.github.spy.sea.core.example.util;

import com.github.spy.sea.core.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/24
 * @since 1.0
 */
@Slf4j
public class PropretiesUtilTest {

    @Test
    public void run16() throws Exception {
        Properties props = PropertiesUtil.load("my.properties");
        log.info("props={}", props);
    }
}
