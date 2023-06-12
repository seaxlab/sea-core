package com.github.seaxlab.core.common;

import com.github.seaxlab.core.component.tracer.enums.TracerProviderEnum;
import com.github.seaxlab.core.config.ConfigurationFactory;
import com.github.seaxlab.core.enums.ConfigKeyEnum;
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

  // set
  public static void setTraceProvider(TracerProviderEnum provider) {
    ConfigurationFactory.getInstance().putString(ConfigKeyEnum.TRACE_PROVIDER.getCode(), provider.getCode());
  }

  // get
  public static TracerProviderEnum getTraceProvider() {
    return TracerProviderEnum.NONE;
  }


  //----------------------- project config begin ---------------------------
  // lock fail exception
  public static RuntimeException EXCEPTION_LOCK_FAIL = null;
  public static RuntimeException EXCEPTION_DB_UPDATE_FAIL = null;

  // business thread pool executor.
  public static ThreadPoolTaskExecutor BIZ_THREAD_POOL_EXECUTOR = null;

  //----------------------- project config end ---------------------------

}
