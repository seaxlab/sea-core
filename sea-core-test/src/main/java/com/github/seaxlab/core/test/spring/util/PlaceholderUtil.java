package com.github.seaxlab.core.test.spring.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.env.MockEnvironment;

import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/22
 * @since 1.0
 */
@Slf4j
public class PlaceholderUtil {

    /**
     * resolve simple placeholder.
     *
     * @param placeholder   p
     * @param propertyName  ''
     * @param propertyValue ''
     * @return
     */
    public static String resolve(String placeholder, String propertyName, String propertyValue) {
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty(propertyName, propertyValue);
        return environment.resolvePlaceholders(placeholder);
    }

    /**
     * resolve placeholder.
     *
     * @param placeholder resolve
     * @param param       map
     * @return
     */
    public static String resolve(String placeholder, Map<String, String> param) {
        MockEnvironment environment = new MockEnvironment();
        param.forEach((key, value) -> environment.setProperty(key, value));
        return environment.resolvePlaceholders(placeholder);
    }
}
