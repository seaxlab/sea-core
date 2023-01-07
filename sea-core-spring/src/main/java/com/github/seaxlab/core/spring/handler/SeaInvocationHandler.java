package com.github.seaxlab.core.spring.handler;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/10
 * @since 1.0
 */
@Slf4j
public class SeaInvocationHandler implements InvocationHandler {
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    return null;
  }
}
