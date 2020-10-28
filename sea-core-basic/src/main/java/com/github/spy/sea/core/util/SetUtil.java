package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;

import java.util.*;

/**
 * set util
 *
 * @author spy
 * @version 1.0 2019-08-14
 * @since 1.0
 */
@Slf4j
public final class SetUtil {

    private SetUtil() {
    }


    /**
     * empty set
     *
     * @return
     */
    public static Set empty() {
        return Collections.emptySet();
    }

    /**
     * list to set
     *
     * @param list
     * @return
     */
    public static Set toSet(List list) {
        return new HashSet(list);
    }

    /**
     * array to set
     *
     * @param arrays
     * @return
     */
    public static Set toSet(Object[] arrays) {
        return new HashSet(Arrays.asList(arrays));
    }

    /**
     * 转换成string，同时带分隔符
     *
     * @param set
     * @param delimiter
     * @return
     */
    public static String toString(Set set, String delimiter) {
        if (set == null) {
            return StringUtil.EMPTY;
        }
        return String.join(delimiter, set);
    }

    /**
     * 两个集合的交集
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        return SetUtils.intersection(a, b);
    }

    /**
     * 多个集合的交集
     *
     * @param sets
     * @param <T>
     * @return
     */
    public static <T> Set<T> intersection(Set<Set<T>> sets) {
        return sets.stream()
                   .map(HashSet::new)
                   .reduce((s1, s2) -> {
                       s1.retainAll(s2);
                       return s1;
                   }).orElseGet(() -> new HashSet<>(0));
    }

}
