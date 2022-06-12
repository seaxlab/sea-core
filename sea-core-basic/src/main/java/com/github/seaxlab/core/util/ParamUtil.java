package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/27
 * @since 1.0
 */
@Slf4j
public final class ParamUtil {


    /**
     * generate param str
     * <p>
     * key1=v1&key2=v2
     * </p>
     *
     * @param map
     * @return
     */
    public static String getStr(Map<String, String> map) {

        if (map == null || map.isEmpty()) {
            return "";
        }

        StringBuilder strBuilder = new StringBuilder();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            strBuilder.append(key + "=" + map.get(key) + "&");
        }

        return strBuilder.substring(0, strBuilder.length() - 1);
    }

}
