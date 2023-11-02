package com.github.seaxlab.core.spring.component.mock.interceptor;

import com.github.seaxlab.core.enums.EnvEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.spring.component.mock.annotation.MockMethod;
import com.github.seaxlab.core.spring.context.SpringContextHolder;
import com.github.seaxlab.core.util.BooleanUtil;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.StringUtil;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * mock method interceptor
 *
 * @author spy
 * @version 1.0 2023/11/02
 * @since 1.0
 */
@Slf4j
public class MockMethodInterceptor implements MethodInterceptor {

  @Nullable
  @Override
  public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
    MockMethod anno = AnnotatedElementUtils.findMergedAnnotation(invocation.getMethod(), MockMethod.class);
    if (anno == null) {
      return invocation.proceed();
    }
    ApplicationContext ctx = SpringContextHolder.getApplicationContext();

    String env = ctx.getEnvironment().getProperty("sea.env", "pro");
    if (EqualUtil.isNotEq(env, EnvEnum.LOCAL.getCode())) {
      return invocation.proceed();
    }

    String key = anno.key();
    Class<?> clazz = anno.clazz();
    String method = anno.method();

    if (StringUtil.isBlank(key)) {
      ExceptionHandler.publishMsg("parameter key is invalid");
    }
    if (Objects.isNull(clazz)) {
      ExceptionHandler.publishMsg("parameter clazz is invalid");
    }
    if (StringUtil.isBlank(method)) {
      ExceptionHandler.publishMsg("parameter method is invalid");
    }

    boolean flag = BooleanUtil.isTrue(System.getProperty(key, "false"));
    if (flag) {
      Object bean = ctx.getBean(clazz);
      return MethodUtils.invokeMethod(bean, method, invocation.getArguments());
    }
    log.warn("condition {}=false, so skip", key);
    //
    return invocation.proceed();
  }


}
