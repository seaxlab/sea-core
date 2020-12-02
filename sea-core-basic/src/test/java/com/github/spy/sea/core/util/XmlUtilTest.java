package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.junit.Test;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/30
 * @since 1.0
 */
@Slf4j
public class XmlUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        String url = PathUtil.getPathFromClassPath("demo-config.xml");
        Element el = XmlUtil.getRoot(url);

        DemoConfig config = XmlUtil.getBean(el, DemoConfig.class);
        log.info("{}", config);
    }

    @Data
    public static class DemoConfig {
        private List<User> users;
        private Config config;
        private String content;
        private Integer count;
        // not exist element
        private String id;
    }

    @Data
    public static class User {
        private String id;
        private String name;
    }

    @Data
    public static class Config {
        private Boolean open;
        private Boolean close;
    }
}
