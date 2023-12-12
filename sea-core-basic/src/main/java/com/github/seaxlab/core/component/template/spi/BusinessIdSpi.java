package com.github.seaxlab.core.component.template.spi;

/**
 * business id spi for once business action
 *
 * @author spy
 * @version 1.0 2023/12/12
 * @since 1.0
 */
public interface BusinessIdSpi {

  /**
   * get current business id
   *
   * @return
   */
  String get();
}
