package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
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
public final class PropertiesUtil {


    /**
     * load from file
     *
     * @param path
     * @return
     */
    public static Properties load(String path) {
        try {
            InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
            if (in == null) {
                log.info("load properties [{}] not exist", path);
                return new Properties();
            }
            Properties pros = new Properties();
            pros.load(in);
            return pros;
        } catch (Exception e) {
            log.error("fail to load properties file", e);
        }

        return new Properties();
    }

    /**
     * load into Map
     *
     * @param path
     * @return
     */
    public static Map<String, String> loadForMap(String path) {
        Map<String, String> map = new HashMap<>();
        Properties pros = load(path);
        Enumeration en = pros.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            map.put(key, pros.getProperty(key));
        }
        return map;
    }
}
