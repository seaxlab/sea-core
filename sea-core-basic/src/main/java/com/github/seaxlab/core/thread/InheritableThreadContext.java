package com.github.seaxlab.core.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

/**
 * 线程池场景下使用，同时需要手动清理，因为使用场景较少，因此手动清理
 *
 * @author spy
 * @version 1.0 2021/6/21
 * @since 1.0
 */
@Slf4j
@Deprecated
public class InheritableThreadContext {

  private InheritableThreadContext() {
  }

  private static final TransmittableThreadLocal<String> parentCtx = new TransmittableThreadLocal<>();

}
