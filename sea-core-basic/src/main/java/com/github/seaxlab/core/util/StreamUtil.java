package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
   * @return map
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
   * @return map
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
   * @return map
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
   * @return map
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
   * @return map
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


  /**
   * 求和,返回optional,过滤null元数和null属性元数
   *
   * @param data
   * @param mapFunction
   * @param <T>
   * @return 注意这里返回optional
   */
  public static <T> Optional<BigDecimal> sumBigDecimal(Collection<T> data, Function<T, BigDecimal> mapFunction) {
    if (data == null || data.isEmpty()) {
      log.warn("data is empty");
      return Optional.empty();
    }
    //
    return data.stream()
               .filter(Objects::nonNull)
               .map(mapFunction)
               .filter(Objects::nonNull)
               //返回optional
               .reduce(BigDecimal::add);
  }


  /**
   * 求和,返回optional,过滤null元数和null属性元数
   *
   * @param data
   * @param mapFunction
   * @param <T>
   * @return 注意这里返回optional
   */
  public static <T> Optional<Integer> sumInteger(Collection<T> data, Function<T, Integer> mapFunction) {
    if (data == null || data.isEmpty()) {
      log.warn("data is empty");
      return Optional.empty();
    }
    //
    return data.stream()
               .filter(Objects::nonNull)
               .map(mapFunction)
               .filter(Objects::nonNull)
               .reduce(Integer::sum);
  }

  /**
   * 求和,返回optional,过滤null元数和null属性元数
   *
   * @param data
   * @param mapFunction
   * @param <T>
   * @return 注意这里返回optional
   */
  public static <T> Optional<Long> sumLong(Collection<T> data, Function<T, Long> mapFunction) {
    if (data == null || data.isEmpty()) {
      log.warn("data is empty");
      return Optional.empty();
    }
    //
    return data.stream()
               .filter(Objects::nonNull)
               .map(mapFunction)
               .filter(Objects::nonNull)
               .reduce(Long::sum);
  }
}
