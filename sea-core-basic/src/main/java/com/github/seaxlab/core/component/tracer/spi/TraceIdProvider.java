package com.github.seaxlab.core.component.tracer.spi;

/**
 * trace id provider
 *
 * @author spy
 * @version 1.0 2023/11/19
 * @since 1.0
 */
public interface TraceIdProvider {

  /**
   * get trace id
   *
   * @return
   */
  String get();
}
