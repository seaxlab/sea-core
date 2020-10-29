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
     * check set is empty.
     *
     * @param set
     * @return
     */
    public static boolean isEmpty(Set set) {
        return set == null || set.isEmpty();
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
     * 两个集合是否相等
     *
     * @param set1
     * @param set2
     * @return
     */
    public static boolean isEqual(final Collection<?> set1, final Collection<?> set2) {
        return SetUtils.isEqualSet(set1, set2);
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
     * 多个set的交集
     *
     * @param sets
     * @param <T>
     * @return
     */
    public static <T> Set<T> intersection(Set<Set<T>> sets) {
        if (isEmpty(sets)) {
            log.warn("sets is empty, plz check.");
            return empty();
        }

        if (sets.size() < 2) {
            log.warn("sets size is less than 2, plz check.");
            return empty();
        }


        return sets.stream()
                   .map(HashSet::new)
                   .reduce((s1, s2) -> {
                       s1.retainAll(s2);
                       return s1;
                   }).orElseGet(() -> new HashSet<>(0));
    }

    /**
     * 多个set的交集
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Set<T> intersection(List<Set<T>> list) {
        if (ListUtil.isEmpty(list)) {
            log.warn("list is empty.");
            return empty();
        }
        if (list.size() < 2) {
            log.warn("list size is less then 2, plz check.");
            return empty();
        }

        Set set = toSet(list);
        return intersection(set);
    }

    /**
     * 差集（注意:有序）
     * a中有，b中没有的
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> Set<T> difference(Set<T> a, Set<T> b) {
        return SetUtils.difference(a, b);
    }

    /**
     * 集合差集（注意:有序）
     * 只存在a中和只存在b中的部分
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> Set<T> disjunction(Set<T> a, Set<T> b) {
        return SetUtils.disjunction(a, b);
    }

    /**
     * 并集
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        return SetUtils.union(a, b);
    }

    /**
     * 多个集合的并集
     *
     * @param sets
     * @param <T>
     * @return
     */
    public static <T> Set<T> union(Set<Set<T>> sets) {
        if (isEmpty(sets)) {
            log.warn("sets is empty, plz check.");
            return empty();
        }

        if (sets.size() < 2) {
            log.warn("sets size is less than 2, plz check.");
            return empty();
        }


        return sets.stream()
                   .map(HashSet::new)
                   .reduce((s1, s2) -> {
                       s1.addAll(s2);
                       return s1;
                   }).orElseGet(() -> new HashSet<>(0));
    }

    /**
     * 多个集合的并集
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Set<T> union(List<Set<T>> list) {
        if (ListUtil.isEmpty(list)) {
            log.warn("list is empty.");
            return empty();
        }
        if (list.size() < 2) {
            log.warn("list size is less then 2, plz check.");
            return empty();
        }

        Set set = toSet(list);
        return union(set);
    }


}
