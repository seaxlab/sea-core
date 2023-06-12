package com.github.seaxlab.core.spring.component.async;

/**
 * async service
 *
 * @author spy
 * @version 1.0 2023/06/12
 * @since 1.0
 */
public interface AsyncService {

  void run(Runnable runnable);

  void run(Runnable runnable, boolean asyncFlag);

}
