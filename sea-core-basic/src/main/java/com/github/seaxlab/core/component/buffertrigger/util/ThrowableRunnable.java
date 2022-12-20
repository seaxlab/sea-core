package com.github.seaxlab.core.component.buffertrigger.util;

/**
 * @author w.vela
 */
@FunctionalInterface
public interface ThrowableRunnable<X extends Throwable> {

  void run() throws X;
}
