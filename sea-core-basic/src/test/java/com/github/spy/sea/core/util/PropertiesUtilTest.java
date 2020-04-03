package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/24
 * @since 1.0
 */
@Slf4j
public class PropertiesUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        Properties props = PropertiesUtil.load("app.properties");
        log.info("props={}", props);

        Map<String, String> map = PropertiesUtil.loadForMap("app.properties");
        log.info("map={}", map);
    }

    @Test
    public void notExistTest() throws Exception {
        Properties props = PropertiesUtil.load("not-exist.properties");
        log.info("props={}", props);

        Map<String, String> map = PropertiesUtil.loadForMap("app.properties");
        log.info("map={}", map);
    }
}
