package com.github.seaxlab.core.component.tracer.spi;

/**
 * trace id spi
 *
 * @author spy
 * @version 1.0 2023/11/19
 * @since 1.0
 */
public interface TraceIdSpi {

  /**
   * get trace id
   *
   * @return
   */
  String get();
}
