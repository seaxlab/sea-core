package com.github.seaxlab.core.spring.aop.interceptor;

import com.github.seaxlab.core.spring.aop.config.AopGlobalConfig;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * log public method interceptor
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Slf4j
public class LogPublicMethodInterceptor implements MethodInterceptor {

  @Nullable
  @Override
  public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
    if (!AopGlobalConfig.getLogPublicMethodFlag()) {
      return invocation.proceed();
    }

    Object returnValue = null;
    try {
      returnValue = invocation.proceed();
    } finally {
      log.info("{}, param=[{}],return={}", getPrefix(invocation), invocation.getArguments(), returnValue);
    }
    //
    return returnValue;
  }

  //--------------------------------private
  private String getPrefix(MethodInvocation invocation) {
    String prefix = "";
    try {
      prefix = invocation.getMethod().getDeclaringClass().getSimpleName() + "." + invocation.getMethod().getName();
    } catch (Exception e) {
      log.error("fail to get class and method name info", e);
    }
    //
    return prefix;
  }


}
