package com.github.spy.sea.core.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * List工具
 *
 * @author spy
 */
@Slf4j
public final class ListUtil {

    /**
     * empty list
     *
     * @return
     */
    public static List empty() {
        return Collections.EMPTY_LIST;
    }

    /**
     * list集合是否为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * list集合是否不为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        return true;
    }


    /**
     * 字符串数组转列表
     *
     * @param listStr
     * @return
     */
    public static List<Integer> strToList(String listStr) {
        List<Integer> resultList = Lists.newArrayList();

        if (StringUtils.isEmpty(listStr)) {
            return resultList;
        }

        String[] array = listStr.replace(" ", "").split(",");
        if ((null == array) || (array.length <= 0)) {
            return resultList;
        }

        List<String> list = Arrays.asList(array);
        if (CollectionUtils.isEmpty(list)) {
            return resultList;
        }

        for (String payType : list) {
            try {
                resultList.add(Integer.valueOf(payType.trim()));
            } catch (Exception e) {
                log.error("转换错误", e);
            }
        }
        return resultList;
    }

    /**
     * map value to list
     *
     * @param map
     * @param <V>
     * @return
     */
    public static <K, V> List<V> toList(Map<K, V> map) {

        return new ArrayList<>(map.values());
    }

}
