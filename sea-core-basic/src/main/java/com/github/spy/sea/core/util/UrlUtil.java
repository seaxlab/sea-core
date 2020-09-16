package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/16
 * @since 1.0
 */
@Slf4j
public class UrlUtil {

    /**
     * build url
     *
     * @param url
     * @param key
     * @param value
     * @return
     */
    public static String build(String url, String key, String value) {
        return url + (url.indexOf('?') > 0 ? "&" : "?") + key + "=" + value;
    }

    /**
     * build url
     *
     * @param url
     * @param param
     * @return
     */
    public static String build(String url, Map<String, Object> param) {
        StringBuilder strBuilder = new StringBuilder();

        Iterator iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry elem = (Map.Entry) iterator.next();
            if (StringUtil.isNotEmpty(strBuilder.toString())) {
                strBuilder.append("&");
            }
            strBuilder.append(elem.getKey())
                      .append("=")
                      .append(elem.getValue());
        }

        String finalUrl = url + (url.indexOf('?') > 0 ? "&" : "?") + strBuilder.toString();

        return finalUrl;
    }

}
