package com.github.seaxlab.core.util;

import com.github.seaxlab.core.common.SymbolConst;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.model.Tuple2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

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
    Precondition.checkNotNull(delimiter, "delimiter cannot be null.");

    T el = collection.stream().filter(Objects::nonNull).findFirst().orElse(null);
    ;

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


  /**
   * get min value from data
   *
   * @param data         data
   * @param keyExtractor key extractor
   * @param <T>
   * @param <U>
   * @return
   */
  public static <T, U extends Comparable<? super U>> T min(Collection<T> data,
    Function<? super T, ? extends U> keyExtractor) {
    Precondition.checkNotNull(data);
    return data.stream().min(Comparator.comparing(keyExtractor)).orElse(null);
  }

  /**
   * get min property of entity
   *
   * @param data         data
   * @param keyExtractor key extractor
   * @param <T>
   * @param <U>
   * @return
   */
  public static <T, U extends Comparable<? super U>> U minProperty(Collection<T> data,
    Function<? super T, ? extends U> keyExtractor) {
    Precondition.checkNotNull(data);
    return data.stream().min(Comparator.comparing(keyExtractor)).map(keyExtractor).orElse(null);
  }

  /**
   * get max value from data
   *
   * @param data         data
   * @param keyExtractor key extractor
   * @param <T>
   * @param <U>
   * @return
   */
  public static <T, U extends Comparable<? super U>> T max(Collection<T> data,
    Function<? super T, ? extends U> keyExtractor) {
    Precondition.checkNotNull(data);
    return data.stream().max(Comparator.comparing(keyExtractor)).orElse(null);
  }

  /**
   * get max property of entity
   *
   * @param data         data
   * @param keyExtractor key extractor
   * @param <T>
   * @param <U>
   * @return
   */
  public static <T, U extends Comparable<? super U>> U maxProperty(Collection<T> data,
    Function<? super T, ? extends U> keyExtractor) {
    Precondition.checkNotNull(data);
    return data.stream().max(Comparator.comparing(keyExtractor)).map(keyExtractor).orElse(null);
  }

  /**
   * get min and max entity from data.
   *
   * @param data         data
   * @param keyExtractor key extractor
   * @param <T>
   * @param <U>
   * @return
   */
  public static <T, U extends Comparable<? super U>> Tuple2<T, T> getMinAndMax(Collection<T> data,
    Function<? super T, ? extends U> keyExtractor) {
    Precondition.checkNotNull(data);
    //
    T min = data.stream().min(Comparator.comparing(keyExtractor)).orElse(null);
    T max = data.stream().max(Comparator.comparing(keyExtractor)).orElse(null);
    return Tuple2.of(min, max);
  }

  /**
   * get min and max property from data.
   *
   * @param data         data
   * @param keyExtractor key extractor
   * @param <T>
   * @param <U>
   * @return
   */
  public static <T, U extends Comparable<? super U>> Tuple2<U, U> getMinAndMaxProperty(Collection<T> data,
    Function<? super T, ? extends U> keyExtractor) {
    Precondition.checkNotNull(data);
    //
    U min = data.stream().min(Comparator.comparing(keyExtractor)).map(keyExtractor).orElse(null);
    U max = data.stream().max(Comparator.comparing(keyExtractor)).map(keyExtractor).orElse(null);
    return Tuple2.of(min, max);
  }

  /**
   * union collection
   *
   * @param a   Iterable
   * @param b   Iterable
   * @param <O> object
   * @return collection
   */
  public static <O> Collection<O> union(final Iterable<? extends O> a, final Iterable<? extends O> b) {
    return CollectionUtils.union(a, b);
  }

  /**
   * intersection collection
   *
   * @param a   Iterable
   * @param b   Iterable
   * @param <O> object
   * @return collection
   */
  public static <O> Collection<O> intersection(final Iterable<? extends O> a, final Iterable<? extends O> b) {
    return CollectionUtils.intersection(a, b);
  }

  /**
   * disjunction collection
   *
   * @param a   Iterable
   * @param b   Iterable
   * @param <O> object
   * @return collection
   */
  public static <O> Collection<O> disjunction(final Iterable<? extends O> a, final Iterable<? extends O> b) {
    return CollectionUtils.disjunction(a, b);
  }

}
