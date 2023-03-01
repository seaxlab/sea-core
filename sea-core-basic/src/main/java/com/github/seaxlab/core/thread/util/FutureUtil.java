package com.github.seaxlab.core.thread.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * future util
 *
 * @author spy
 * @version 1.0 2023/03/01
 * @since 1.0
 */
@Slf4j
public final class FutureUtil {

  private FutureUtil() {
  }

  /**
   * wait all futures execute completely
   *
   * @param futures
   * @param <T>
   */
  public static <T> void join(Collection<CompletableFuture<T>> futures) {
    //CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
    CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).join();
  }

  public static <T> List<T> toList(Collection<CompletableFuture<T>> futures) {
    return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
  }

  public static <T> List<T> toList(Collection<CompletableFuture<T>> futures, Predicate<? super T> predicate) {
    return futures.stream().map(CompletableFuture::join)
//      .filter(Objects::nonNull)
      .filter(predicate)//
      .collect(Collectors.toList());
  }

}
