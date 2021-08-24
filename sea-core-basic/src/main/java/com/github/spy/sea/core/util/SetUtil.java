package com.github.spy.sea.core.util;

import com.github.spy.sea.core.common.SymbolConst;
import com.github.spy.sea.core.model.Diff;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * check set is not empty.
     *
     * @param set
     * @return
     */
    public static boolean isNotEmpty(Set set) {
        return set != null && !set.isEmpty();
    }

    /**
     * list to set
     * 注意：如果list中对象有相同的hashCode，则不会重复添加
     *
     * @param list
     * @return
     */
    public static Set toSet(List list) {
        return new HashSet(list);
    }

    /**
     * list to set
     *
     * @param <I>  input
     * @param <R>  return
     * @param list data
     * @param func convert function
     * @return set
     */
    public static <I, R> Set<R> toSet(List<I> list, Function<I, R> func) {
        if (list == null || list.isEmpty()) {
            return empty();
        }

        return list.stream()
                   .map(func)
                   .collect(Collectors.toSet());
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
     * to string
     *
     * @param set data
     * @return string
     */
    public static String toString(Set set) {
        return toString(set, SymbolConst.COMMA);
    }

    /**
     * 转换成string，同时带分隔符
     *
     * @param set       简单的Set<String>
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
     * 分隔符
     * <pre>
     *      SetUtil.toString(set, User::getName, ",");
     *      SetUtil.toString(set, user->user.getName(), ",");
     * </pre>
     *
     * @param set
     * @param fn        获取某个字段只
     * @param delimiter
     * @param <T>
     * @return
     */
    public static <T> String toString(Set<T> set, Function<? super T, String> fn, String delimiter) {
        if (set == null) {
            return StringUtil.EMPTY;
        }
        return set.stream().map(fn).collect(Collectors.joining(delimiter));
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
     * get first one.
     *
     * @param set
     * @param <T>
     * @return
     */
    public static <T> Optional<T> first(Set<T> set) {
        if (isEmpty(set)) {
            return Optional.ofNullable(null);
        }
        if (set.size() > 1) {
            log.warn("when get one, while set size > 1");
        }
        return Optional.ofNullable(set.stream().findFirst().get());
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

        if (sets.size() == 1) {
            log.warn("sets size is 1.");
            return sets.stream().findFirst().get();
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
        if (list.size() == 1) {
            log.warn("list size is 1.");
            return list.get(0);
        }

        Set<Set<T>> set = toSet(list);
        // 防止list中有重复的set
        if (set.size() == 1) {
            return set.stream().findFirst().get();
        }

        return intersection(set);
    }

    /**
     * 差集
     * <p>
     * 注意参数顺序；a中有，b中没有
     * </p>
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
     * 返回差异对象
     *
     * <p>
     * 注意参数顺序
     * </p>
     *
     * @param a   左侧
     * @param b   右侧
     * @param <T>
     * @return
     */
    public static <T> Diff<T> diff(Set<T> a, Set<T> b) {
        Diff<T> diff = new Diff<>();

        // 交集
        diff.setIntersections(intersection(a, b));

        // only left has
        diff.setLefts(difference(a, b));

        // only right has
        diff.setRights(difference(b, a));

        return diff;
    }


    /**
     * 集合差集
     * <p>
     * 注意参数顺序；只存在a中和只存在b中的部分
     * </p>
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

        if (sets.size() == 1) {
            log.warn("sets size is 1.");
            return sets.stream().findFirst().get();
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
        if (list.size() == 1) {
            return list.get(0);
        }

        Set set = toSet(list);

        if (set.size() == 1) {
            return set;
        }

        return union(set);
    }


}
