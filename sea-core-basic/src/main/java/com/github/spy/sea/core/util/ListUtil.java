package com.github.spy.sea.core.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
     * @return -
     */
    public static List empty() {
        return Collections.EMPTY_LIST;
    }

    /**
     * list集合是否为空
     *
     * @param list -
     * @return -
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
     * @param list -
     * @return -
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * check is all empty
     *
     * @param lists multi list
     * @return boolean
     */
    public static boolean isAllEmpty(List<?>... lists) {
        for (List<?> list : lists) {
            if (isNotEmpty(list)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 字符串数组转列表
     *
     * @param listStr -
     * @return -
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
        if (isEmpty(list)) {
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
        if (map == null || map.isEmpty()) {
            return empty();
        }
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
        // cannot use Arrays.asList, because it is immutable.
        return Lists.newArrayList(array);
    }

    /**
     * T[] to list
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(T[] array) {
        if (array == null || array.length == 0) {
            return empty();
        }
        return Lists.newArrayList(array);
    }

    /**
     * new array list
     *
     * @param e
     * @param <E>
     * @return
     */
    public static <E> List<E> newArrayList(E... e) {
        if (e == null || e.length == 0) {
            return empty();
        }
        return Lists.newArrayList(e);
    }

    /**
     * set to list.
     *
     * @param set
     * @return
     */
    public static <T> List<T> toList(Set<T> set) {
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

    /**
     * filter empty item and distinct them.
     *
     * @param data list data
     * @return
     */
    public static List<String> distinct(List<String> data) {
        if (isEmpty(data)) {
            return empty();
        }
        return data.stream()
                   .filter(item -> StringUtil.isNotEmpty(item))
                   .distinct()
                   .collect(Collectors.toList());
    }

    /**
     * distinct obj
     *
     * @param data
     * @param function
     * @param <T>
     * @return
     */
    public static <T> List<T> distinctObj(List<T> data, Function<? super T, ?> function) {
        return data.stream().filter(distinctByKey(function)).collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * 交换两个元素位置
     *
     * @param list list
     * @param i    index
     * @param j    index
     * @param <E>  entity.
     */
    public static <E> void swap(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }


    /**
     * 分页
     *
     * @param data
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> page(List<T> data, int pageSize) {
        List<List<T>> list = new ArrayList<>();
        int pageNum = 1;
        boolean hasNext = true;

        while (hasNext) {
            List<T> subList = page(data, pageNum, pageSize);
            if (isEmpty(subList)) {
                break;
            }
            if (subList.size() < pageSize) {
                break;
            }
            list.add(subList);
        }

        return list;
    }

    /**
     * page
     *
     * @param data
     * @param pageNum
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<T> page(List<T> data, int pageNum, int pageSize) {

        if (data == null) {
            return empty();
        }
        if (data.size() == 0) {
            return empty();
        }

        List<T> listSort;
        int size = data.size();
        int pageStart = pageNum == 1 ? 0 : (pageNum - 1) * pageSize;//截取的开始位置
        int pageEnd = size < pageNum * pageSize ? size : pageNum * pageSize;//截取的结束位置

        if (pageStart < size) {
            listSort = data.subList(pageStart, pageEnd);
        } else {
            listSort = empty();
        }

        return listSort;
    }


}
