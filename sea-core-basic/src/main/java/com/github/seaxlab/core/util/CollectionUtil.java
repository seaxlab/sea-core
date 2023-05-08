package com.github.seaxlab.core.util;

import com.github.seaxlab.core.common.SymbolConst;
import com.github.seaxlab.core.exception.Precondition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/28
 * @since 1.0
 */
@Slf4j
public final class CollectionUtil {


  private CollectionUtil() {
  }

  /**
   * check collection is empty
   *
   * @param collection
   * @return
   */
  public static boolean isEmpty(Collection<?> collection) {
    return (collection == null || collection.isEmpty());
  }

  /**
   * check collection is not empty
   *
   * @param collection
   * @return
   */
  public static boolean isNotEmpty(Collection<?> collection) {
    return !isEmpty(collection);
  }

  /**
   * check all empty.
   *
   * @param collections multi collection
   * @return boolean
   */
  public static boolean isAllEmpty(Collection<?>... collections) {
    for (Collection<?> collection : collections) {
      if (isEmpty(collection)) {
      } else {
        return false;
      }
    }
    return true;
  }

  /**
   * check map is empty
   *
   * @param map
   * @return
   */
  public static boolean isEmpty(Map<?, ?> map) {
    return (map == null || map.isEmpty());
  }

  /**
   * check map is not empty
   *
   * @param map
   * @return
   */
  public static boolean isNotEmpty(Map<?, ?> map) {
    return !isEmpty(map);
  }

  /**
   * swap two element
   *
   * @param c1
   * @param c2
   */
  public static <E> void swap(Collection<E> c1, Collection<E> c2) {
    Collection<E> tmp = c2;
    c2 = c1;
    c1 = tmp;
  }


  /**
   * collection to set
   *
   * @param collection
   * @param func
   * @param <E>
   * @param <R>
   * @return
   */
  public static <E, R> Set<R> toSet(Collection<E> collection, Function<E, R> func) {
    if (collection == null || collection.isEmpty()) {
      return new HashSet<>();
    }

    return collection.stream().map(func).collect(Collectors.toSet());
  }

  /**
   * collection to set
   *
   * @param collection
   * @param func
   * @param <E>
   * @param <R>
   * @return
   */
  public static <E, R> List<R> toList(Collection<E> collection, Function<E, R> func) {
    if (collection == null || collection.isEmpty()) {
      return new ArrayList<>();
    }

    return collection.stream().map(func).collect(Collectors.toList());
  }

  public static <T> String toString(Collection<T> collection) {
    return toString(collection, SymbolConst.COMMA);
  }

  public static <T> String toString(Collection<T> collection, String delimiter) {
    if (collection == null || collection.isEmpty()) {
      return "";
    }
    Precondition.checkNotBlank(delimiter, "delimiter cannot be empty.");

    T el = collection.stream().findAny().get();

    if (el != null && ClassUtil.isSimpleType(el.getClass())) {
      return toString(collection, item -> String.valueOf(item), delimiter);
    }
    log.warn("first element is null, so no execute toString function.");
    return StringUtil.EMPTY;
  }


  public static <T> String toString(Collection<T> collection, Function<T, String> fn) {
    return toString(collection, fn, SymbolConst.COMMA);
  }

  public static <T> String toString(Collection<T> collection, Function<T, String> fn, String delimiter) {
    if (isEmpty(collection)) {
      return StringUtil.EMPTY;
    }
    return collection.stream().map(fn).collect(Collectors.joining(delimiter));
  }

}
