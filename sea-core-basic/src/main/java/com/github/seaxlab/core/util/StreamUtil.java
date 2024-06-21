package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Stream util
 *
 * @author spy
 * @version 1.0 2024/6/20
 * @since 1.0
 */
@Slf4j
public final class StreamUtil {

  private StreamUtil() {
  }

  /**
   * convert collection to map
   *
   * @param data      input collection
   * @param keyMapper key mapper function
   * @param <R>       entity
   * @param <K>       key
   * @return
   */
  public static <K, R> Map<K, R> toMap(Collection<R> data, Function<? super R, ? extends K> keyMapper) {
    if (data == null || data.isEmpty()) {
      log.warn("data is empty");
      return new HashMap<>();
    }
    return data.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
  }

  /**
   * convert list to a new map.
   *
   * @param data        input collection
   * @param keyMapper   new key
   * @param valueMapper new value
   * @param <K>
   * @param <R>
   * @param <E>
   * @return
   */
  public static <K, R, E> Map<K, E> toMap(Collection<R> data, Function<? super R, ? extends K> keyMapper,
    Function<? super R, ? extends E> valueMapper) {
    if (data == null || data.isEmpty()) {
      log.warn("data is empty");
      return new HashMap<>();
    }
    return data.stream().collect(Collectors.toMap(keyMapper, valueMapper));
  }

  /**
   * to distinct map if have multi [unique key]
   *
   * @param data      input collection
   * @param keyMapper key mapper function
   * @param <K>
   * @param <R>
   * @return
   */
  public static <K, R> Map<K, R> toDistinctMap(Collection<R> data, Function<? super R, ? extends K> keyMapper) {
    if (data == null || data.isEmpty()) {
      log.warn("data is empty");
      return new HashMap<>();
    }
    return data.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (o1, o2) -> o1));
  }

  /**
   * convert list to Map<K ,List<R>>
   *
   * @param data      input collection
   * @param keyMapper key mapper function
   * @param <K>
   * @param <R>
   * @return
   */
  public static <K, R> Map<K, List<R>> toMapList(Collection<R> data, Function<? super R, ? extends K> keyMapper) {
    if (data == null || data.isEmpty()) {
      log.warn("data is empty");
      return new HashMap<>();
    }
    return data.stream().collect(Collectors.groupingBy(keyMapper));
  }

  /**
   * <p>
   * convert list to map[key,List[Value]]
   * </p>
   *
   * @param data        input collection
   * @param keyMapper   key mapper
   * @param valueMapper value mapper
   * @param <E>         entity
   * @param <A>         map key type
   * @param <B>         map value type
   * @return
   */
  public static <E, A, B> Map<A, List<B>> toMapList(Collection<E> data, Function<? super E, ? extends A> keyMapper,
    Function<? super E, ? extends B> valueMapper) {
    if (data == null || data.isEmpty()) {
      log.warn("data is empty");
      return new HashMap<>();
    }

    return data.stream()
               .collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
  }


}
