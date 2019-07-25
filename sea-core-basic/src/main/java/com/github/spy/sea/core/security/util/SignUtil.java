package com.github.spy.sea.core.security.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class SignUtil {

    /**
     * 获取md5方式摘要
     *
     * @param map
     * @return
     */
    public static String getByMd5(Map<String, String> map) {

        Map treeMap = new TreeMap();
        for (String key : map.keySet()) {
            if ("sign_type".equals(key)) {
                continue;
            }
            if ("sign".equals(key)) {
                continue;
            }
            if (StringUtils.isEmpty(map.get(key))) {
                continue;
            }
            treeMap.put(key, map.get(key));
        }

        StringBuilder strBuilder = new StringBuilder();
        Iterator it = treeMap.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            strBuilder.append(key + "=" + treeMap.get(key) + "&");
        }
        String message = strBuilder.substring(0, strBuilder.length() - 1);
        return Md5Util.getDigest(message).toUpperCase();
    }

    /**
     * 判断是否有效签名
     *
     * @param map
     * @param sign
     * @return
     */
    public static boolean isValid(Map<String, String> map, String sign) {
        String expectSign = getByMd5(map);

        return expectSign.equalsIgnoreCase(sign);
    }

}
