package com.github.seaxlab.core.spring.aop.interceptor;

import com.github.seaxlab.core.thread.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * thread context
 * <doc> 只能用在程序入口</doc>
 *
 * @author spy
 * @version 1.0 2021/2/6
 * @since 1.0
 */
@Slf4j
public class ThreadContextMethodInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Object result;
    try {
      result = invocation.proceed();
    } finally {
      ThreadContext.clean();
    }

    return result;
  }
}
