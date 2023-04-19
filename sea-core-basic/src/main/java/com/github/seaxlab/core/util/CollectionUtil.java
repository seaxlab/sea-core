package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

  public static String join(Collection<String> collection) {
    if (collection == null || collection.isEmpty()) {
      return "";
    }
    return String.join(",", collection);
  }

}
