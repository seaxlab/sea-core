package com.github.seaxlab.core.spring.component.async.service;

import com.github.seaxlab.core.common.SeaGlobalConfig;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.spring.component.async.AsyncService;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;

/**
 * default async service
 *
 * @author spy
 * @version 1.0 2023/6/12
 * @since 1.0
 */
@Slf4j
public class DefaultAsyncService implements AsyncService {

  @Override
  public void run(Runnable runnable) {
    log.info("try to run in async.");
    Precondition.checkNotNull(SeaGlobalConfig.BIZ_THREAD_POOL_EXECUTOR, "biz thread pool executor cannot empty.");
    CompletableFuture.runAsync(runnable, SeaGlobalConfig.BIZ_THREAD_POOL_EXECUTOR) //
      .exceptionally(t -> {
        log.error("fail to async run, exception=", t);
        return null;
      });
  }

  @Override
  public void run(Runnable runnable, boolean asyncFlag) {
    if (asyncFlag) {
      log.info("try to run in async.");
      Precondition.checkNotNull(SeaGlobalConfig.BIZ_THREAD_POOL_EXECUTOR, "biz thread pool executor cannot empty.");
      //
      CompletableFuture.runAsync(runnable, SeaGlobalConfig.BIZ_THREAD_POOL_EXECUTOR) //
        .exceptionally(t -> {
          log.error("fail to async run, exception=", t);
          return null;
        });
    } else {
      log.info("try to run in sync.");
      runnable.run();
    }
  }
}
