package com.github.seaxlab.core.component.service.lifecycle;

/**
 * destroy life cycle
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
public interface AbstractDestroyLifeCycle {

  /**
   * release resource
   */
  void destroy();
}
