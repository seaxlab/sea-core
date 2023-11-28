package com.github.seaxlab.core.component.tracer.util;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.component.tracer.spi.TraceIdSpi;
import com.github.seaxlab.core.loader.EnhancedServiceLoader;
import lombok.extern.slf4j.Slf4j;

/**
 * trace util
 *
 * @author spy
 * @version 1.0 2023/11/28
 * @since 1.0
 */
@Slf4j
public final class TraceUtil {

  private TraceUtil() {
  }

  /**
   * get trace id
   *
   * @return trace id
   */
  public static String getTraceId() {
    try {
      TraceIdSpi traceIdSpi = EnhancedServiceLoader.load(TraceIdSpi.class);
      return traceIdSpi.get();
    } catch (Exception e) {
      log.warn("fail to get trace id by spi");
    }
    return CoreConst.NOT_APPLICABLE;
  }

}
