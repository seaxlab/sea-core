package com.github.spy.sea.core.util;

import com.github.spy.sea.core.common.SymbolConst;
import com.github.spy.sea.core.exception.Precondition;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return list == null || list.isEmpty();
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
     * return first element
     *
     * @param list data
     * @param <E>  entity
     * @return first element
     */
    public static <E> E first(List<E> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * return last element
     *
     * @param list data
     * @param <E>  entity
     * @return last element
     */
    public static <E> E last(List<E> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }


    /**
     * 字符串数组转列表
     *
     * @param listStr -
     * @return -
     */
    @Deprecated
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

        for (String item : list) {
            try {
                resultList.add(Integer.valueOf(item.trim()));
            } catch (Exception e) {
                log.error("转换错误", e);
            }
        }
        return resultList;
    }

    public static List<String> toStrList(String listStr) {
        return toStrList(listStr, ",");
    }

    public static List<String> toStrList(String listStr, String split) {
        List<String> resultList = Lists.newArrayList();

        if (StringUtils.isEmpty(listStr)) {
            return resultList;
        }

        String[] array = listStr.trim().split(split);
        if ((null == array) || (array.length <= 0)) {
            return resultList;
        }

        List<String> list = Arrays.asList(array);
        if (isEmpty(list)) {
            return resultList;
        }
        return list.stream().map(item -> item.trim()).collect(Collectors.toList());
    }

    public static List<Integer> toIntList(String listStr) {
        return toIntList(listStr, ",");
    }

    public static List<Integer> toIntList(String listStr, String split) {
        List<Integer> resultList = Lists.newArrayList();

        if (StringUtils.isEmpty(listStr)) {
            return resultList;
        }

        String[] array = listStr.trim().split(split);
        if ((null == array) || (array.length <= 0)) {
            return resultList;
        }

        List<String> list = Arrays.asList(array);
        if (isEmpty(list)) {
            return resultList;
        }
        return list.stream().map(item -> {
            String value = item.trim();
            if (value.isEmpty()) {
                return null;
            }
            try {
                return Integer.valueOf(value);
            } catch (Exception e) {
                log.error("fail to convert int", e);
            }
            return null;
        }).collect(Collectors.toList());
    }

    public static List<Long> toLongList(String listStr) {
        return toLongList(listStr, ",");
    }

    public static List<Long> toLongList(String listStr, String split) {
        List<Long> resultList = Lists.newArrayList();

        if (StringUtils.isEmpty(listStr)) {
            return resultList;
        }

        String[] array = listStr.trim().split(split);
        if ((null == array) || (array.length <= 0)) {
            return resultList;
        }

        List<String> list = Arrays.asList(array);
        if (isEmpty(list)) {
            return resultList;
        }
        return list.stream().map(item -> {
            String value = item.trim();
            if (value.isEmpty()) {
                return null;
            }
            try {
                return Long.valueOf(value);
            } catch (Exception e) {
                log.error("fail to convert long", e);
            }
            return null;
        }).collect(Collectors.toList());
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
     * 多个list合并
     *
     * @param list1
     * @param args
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(List<T> list1, List<T>... args) {
        List<T> newList = new ArrayList<>();
        if (isNotEmpty(list1)) {
            newList.addAll(list1);
        }

        if (args != null && args.length != 0) {
            for (List<T> tempList : args) {
                if (isNotEmpty(tempList)) {
                    newList.addAll(tempList);
                }
            }
        }
        return newList;
    }

    /**
     * 多个list相加
     *
     * @param list1
     * @param args
     * @param <T>
     * @return
     */
    public static <T> List<T> add(List<T> list1, List<T>... args) {
        return toList(list1, args);
    }

    /**
     * add all if necessary.
     *
     * @param allList
     * @param part
     * @param <T>
     */
    public static <T> void addAllIfNecessary(List<T> allList, List<T> part) {
        Precondition.checkNotNull(allList);
        if (isNotEmpty(part)) {
            allList.addAll(part);
        }
    }


    /**
     * 初始化指定大小的数组容量，超过该容量后才会扩容
     *
     * @param initialCapacity initial capacity
     * @param <E>
     * @return
     */
    public static <E> List<E> newNoResizeArrayList(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
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
     * iterator to List
     *
     * @param iterator
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(Iterator<T> iterator) {
        return Lists.newArrayList(iterator);
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
     * convert complex to simple R
     *
     * @param list data
     * @param func convert function
     * @param <T>  input
     * @param <R>  return
     * @return list
     */
    public static <T, R> List<R> toList(List<T> list, Function<T, R> func) {
        if (isEmpty(list)) {
            return empty();
        }
        return list.stream().map(func).collect(Collectors.toList());
    }

    /**
     * convert complex to simple R
     *
     * @param list data
     * @param func convert function
     * @param <T>  input
     * @param <R>  return
     * @return list
     */
    public static <T, R> List<R> toListDistinct(List<T> list, Function<T, R> func) {
        if (isEmpty(list)) {
            return empty();
        }
        return list.stream().map(func).distinct().collect(Collectors.toList());
    }


    /**
     * remove one element
     * 如果有多个重复元素，只删除其中一个
     *
     * @param list
     * @param element
     * @param <T>
     */
    public static <T> void remove(List<T> list, T element) {
        if (list == null) {
            return;
        }
        list.remove(element);
    }

    public static <T> void remove(List<T> list, Collection<T> elements) {
        if (list == null) {
            return;
        }
        list.remove(elements);
    }


    /**
     * 删除元素
     *
     * @param list
     * @param predicate
     */
    public static <T> void remove(List<T> list, Predicate<T> predicate) {
        int size = list.size();
        // from 1.8
        list.removeIf(predicate);
        log.info("before:{},after:{}", size, list.size());
    }

    /**
     * 删除元素
     *
     * @param list
     * @param predicate
     */
    @Deprecated
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
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    /**
     * to map, if have multi one, then execute binary operator.
     *
     * @param list
     * @param keyMapper
     * @param binaryOperator distinct which one
     * @param <K>
     * @param <R>
     * @return
     */
    public static <K, R> Map<K, R> toMap(List<R> list, Function<? super R, ? extends K> keyMapper, BinaryOperator<R> binaryOperator) {
        if (isEmpty(list)) {
            return MapUtil.empty();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), binaryOperator));
    }

    /**
     * to distinct map if have multi [unique key]
     *
     * @param list
     * @param keyMapper
     * @param <K>
     * @param <R>
     * @return
     */
    public static <K, R> Map<K, R> toDistinctMap(List<R> list, Function<? super R, ? extends K> keyMapper) {
        if (isEmpty(list)) {
            return MapUtil.empty();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (o1, o2) -> o1));
    }


    public static <K, R> Map<K, R> toMap(List<R> list, Predicate<? super R> predicate, Function<? super R, ? extends K> keyMapper) {
        if (isEmpty(list)) {
            return MapUtil.empty();
        }
        Stream<R> stream = list.stream();

        if (predicate != null) {
            stream = stream.filter(predicate);
        }

        return stream.collect(Collectors.toMap(keyMapper, Function.identity()));
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
        return list.stream().collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * <p>
     * convert list to map[key,List[Value]]
     * </p>
     *
     * @param list        data
     * @param keyMapper   key mapper
     * @param valueMapper value mapper
     * @param <E>         entity
     * @param <A>         map key type
     * @param <B>         map value type
     * @return
     */
    public static <E, A, B> Map<A, List<B>> toMapList(List<E> list, Function<? super E, ? extends A> keyMapper, Function<? super E, ? extends B> valueMapper) {
        if (isEmpty(list)) {
            return MapUtil.empty();
        }

        return list.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
    }

    /**
     * to map count
     *
     * @param list
     * @param keyMapper
     * @param <E>
     * @param <A>
     * @return
     */
    public static <E, A> Map<A, Long> toMapCount(List<E> list, Function<? super E, ? extends A> keyMapper) {
        if (isEmpty(list)) {
            return MapUtil.empty();
        }

        return list.stream().collect(Collectors.groupingBy(keyMapper, Collectors.counting()));
    }

    /**
     * <p>
     * convert list(A(List p)) to map[key,List[Value]]
     * </p>
     *
     * @param list        data
     * @param keyMapper   key mapper
     * @param valueMapper value mapper 注意这里是stream，需要注意是否为空
     * @param <E>         entity
     * @param <A>         map key type
     * @param <B>         map value type
     * @return
     */
    public static <E, A, B> Map<A, List<B>> toMapFlatList(List<E> list, Function<? super E, ? extends A> keyMapper, Function<? super E, ? extends Stream<? extends B>> valueMapper) {
        if (isEmpty(list)) {
            return MapUtil.empty();
        }

        return list.stream().collect(Collectors.groupingBy(keyMapper, flatMapping(valueMapper, Collectors.toList())));
    }


    private static <T, U, A, R> Collector<T, ?, R> flatMapping(Function<? super T, ? extends Stream<? extends U>> mapper, Collector<? super U, A, R> downstream) {
        BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
        return Collector.of(downstream.supplier(), (r, t) -> mapper.apply(t).sequential().forEach(u -> downstreamAccumulator.accept(r, u)), downstream.combiner(), downstream.finisher(), downstream.characteristics().stream().toArray(Collector.Characteristics[]::new));
    }

    /**
     * <p>
     * convert list(A(List p)) to map[key,List[Value]]
     * </p>
     *
     * @param list        data
     * @param keyMapper   key mapper
     * @param valueMapper value mapper
     * @param <E>         entity
     * @param <A>         map key type
     * @param <B>         map value type
     * @return
     */
    public static <E, A, B> Map<A, List<B>> toMapFlatList2(List<E> list, Function<? super E, ? extends A> keyMapper, Function<? super E, List<B>> valueMapper) {
        if (isEmpty(list)) {
            return MapUtil.empty();
        }

        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, (old, newValue) -> {
            List<B> all = new ArrayList<>();
            if (old != null) {
                all.addAll(old);
            }
            if (newValue != null) {
                all.addAll(newValue);
            }
            return all;
        }));
    }

    /**
     * to flat list
     *
     * @param list -
     * @param <E>  entity.
     * @return
     */
    public static <E> List<E> toFlatList(List<List<E>> list) {

        if (isEmpty(list)) {
            return empty();
        }

        return list.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * to flat list with draw list field of E
     *
     * @param list
     * @param mapper
     * @param <E>
     * @param <R>
     * @param <T>
     * @return
     */
    public static <E, R, T> List<R> toFlatList(List<E> list, Function<? super E, List<? extends R>> mapper) {

        if (isEmpty(list)) {
            return empty();
        }

        return list.stream().map(mapper).flatMap(Collection::stream).collect(Collectors.toList());
    }


    /**
     * to string
     *
     * @param list data
     * @param <T>  class
     * @return string
     */
    public static <T> String toString(List<T> list) {
        if (isEmpty(list)) {
            return StringUtil.EMPTY;
        }

        T el = list.get(0);

        if (el != null) {
            if (ClassUtil.isSimpleType(el.getClass())) {
                return toString(list, item -> String.valueOf(item), SymbolConst.COMMA);
            }
        }
        log.warn("first element is null, so no execute toString function.");
        return StringUtil.EMPTY;
    }

    /**
     * to string
     *
     * @param list data
     * @param <T>  class
     * @return string
     */
    public static <T> String toString(List<T> list, Function<T, String> fn) {
        return toString(list, fn, SymbolConst.COMMA);
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
     * filter
     *
     * @param list      list data
     * @param predicate predicate func
     * @param <T>       entity
     * @return new list
     */
    public static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        if (isEmpty(list)) {
            return empty();
        }

        return list.stream().filter(predicate).collect(Collectors.toList());
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
        return data.stream().filter(item -> StringUtil.isNotEmpty(item)).distinct().collect(Collectors.toList());
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
     * remove null element
     *
     * @param list
     * @param <E>
     */
    public static <E> void removeNull(List<E> list) {
        if (isEmpty(list)) {
            return;
        }
        list.removeAll(Collections.singleton(null));
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

    public static <E> void swap(List<E> list1, List<E> list2) {
        Precondition.checkNotEmpty(list1, "list1不能为空");
        Precondition.checkNotEmpty(list2, "list2不能为空");

        List<E> tmp = list2;
        list2 = list1;
        list1 = tmp;
    }

    /**
     * sum
     *
     * @param data
     * @param mapper
     * @param <T>
     * @return
     */
    public static <T> int sum(List<T> data, ToIntFunction<? super T> mapper) {
        if (isEmpty(data)) {
            return 0;
        }
        return data.stream().mapToInt(mapper).sum();
    }

    /**
     * sum
     *
     * @param data
     * @param mapper
     * @param <T>
     * @return
     */
    public static <T> long sum(List<T> data, ToLongFunction<? super T> mapper) {
        if (isEmpty(data)) {
            return 0;
        }
        return data.stream().mapToLong(mapper).sum();
    }

    /**
     * sum
     *
     * @param data
     * @param mapper
     * @param <T>
     * @return
     */
    public static <T> double sum(List<T> data, ToDoubleFunction<? super T> mapper) {
        if (isEmpty(data)) {
            return 0;
        }
        return data.stream().mapToDouble(mapper).sum();
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
                hasNext = false;
            }
            list.add(subList);
            pageNum++;
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

        if (isEmpty(data)) {
            return empty();
        }

        List<T> part;
        int size = data.size();
        int pageStart = pageNum == 1 ? 0 : (pageNum - 1) * pageSize;//截取的开始位置
        int pageEnd = size < pageNum * pageSize ? size : pageNum * pageSize;//截取的结束位置

        if (pageStart < size) {
            // page start include
            // page end exclude
            part = data.subList(pageStart, pageEnd);
        } else {
            part = empty();
        }

        return part;
    }

    public static void sortIntegerAsc(List<Integer> data) {
        if (data == null) {
            return;
        }
        Collections.sort(data);
    }

    public static void sortIntegerDesc(List<Integer> data) {
        if (data == null) {
            return;
        }
        Collections.sort(data, (o1, o2) -> Integer.compare(o2, o1));
    }

    public static void sortLongAsc(List<Long> data) {
        if (data == null) {
            return;
        }
        Collections.sort(data);
    }

    public static void sortLongDesc(List<Long> data) {
        if (data == null) {
            return;
        }
        Collections.sort(data, (o1, o2) -> Long.compare(o2, o1));
    }


    /**
     * list intersection注意顺序
     *
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T> List<T> intersection(final List<? extends T> list1, final List<? extends T> list2) {
        if (list1 == null || list2 == null) {
            return new ArrayList<>();
        }

        List<T> list = new ArrayList<>();
        for (T t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

}
