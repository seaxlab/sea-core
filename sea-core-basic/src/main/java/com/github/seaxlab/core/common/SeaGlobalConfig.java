package com.github.seaxlab.core.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * sea global config
 *
 * @author spy
 * @version 1.0 2022/9/1
 * @since 1.0
 */
@Slf4j
public class SeaGlobalConfig {

  private SeaGlobalConfig() {
  }

  //----------------------- project config begin ---------------------------
  // 锁失败异常
  public static RuntimeException EXCEPTION_LOCK_FAIL = null;
  // 数据库更新失败异常
  public static RuntimeException EXCEPTION_DB_UPDATE_FAIL = null;

  // 业务线程池
  public static ThreadPoolTaskExecutor BIZ_THREAD_POOL_EXECUTOR = null;

  //----------------------- project config end ---------------------------

}
