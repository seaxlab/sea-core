package com.github.spy.sea.core.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

        // or return map.values().stream().collect(Collectors.toList());

        return new ArrayList<>(map.values());
    }

    /**
     * String[] to list
     *
     * @param array
     * @return
     */
    public static List<String> toList(String[] array) {
        if (array == null || array.length == 0) {
            return empty();
        }

        return Arrays.asList(array);
    }

    /**
     * set to list.
     *
     * @param set
     * @return
     */
    public static List<String> toList(Set<String> set) {
        return new ArrayList<>(set);
    }


    /**
     * 删除元素
     *
     * @param list
     * @param predicate
     */
    public static <T> void delete(List<T> list, Predicate<T> predicate) {
        int size = list.size();
        // from 1.8
        list.removeIf(predicate);
        log.info("before:{},after:{}", size, list.size());
    }

    /**
     * convert list to map
     *
     * @param list
     * @param keyMapper
     * @param <R>
     * @param <K>
     * @return
     */
    public static <K, R> Map<K, R> toMap(List<R> list, Function<? super R, ? extends K> keyMapper) {
        if (isEmpty(list)) {
            return MapUtil.empty();
        }
        return list.stream()
                   .collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    /**
     * convert list to Map<K ,List<R>>
     *
     * @param list
     * @param keyMapper
     * @param <K>
     * @param <R>
     * @return
     */
    public static <K, R> Map<K, List<R>> toMapList(List<R> list, Function<? super R, ? extends K> keyMapper) {
        if (isEmpty(list)) {
            return MapUtil.empty();
        }
        return list.stream()
                   .collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * toString
     * <pre>
     *  ListUtil.toString(users, User::getName, ",")
     *  ListUtil.toString(users, user->user.getName(), ",")
     *  n0,n1,n2,n3,n4,n5,n6,n7,n8,n9
     * </pre>
     *
     * @param list      data
     * @param fn        function
     * @param delimiter delimiter
     * @param <T>
     * @return
     */
    public static <T> String toString(List<T> list, Function<T, String> fn, String delimiter) {
        if (isEmpty(list)) {
            return StringUtil.EMPTY;
        }
        return list.stream().map(fn).collect(Collectors.joining(delimiter));
    }

}
