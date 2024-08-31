package com.github.seaxlab.core.spring.component.tunnel.util;

import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteArgumentInfoBO;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteReqBO;
import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.ClassUtil;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.ExceptionUtil;
import com.github.seaxlab.core.util.ReflectUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * spring context util
 *
 * @author spy
 * @version 1.0 2023/09/01
 * @since 1.0
 */
@Slf4j
public final class TunnelUtil {

  private TunnelUtil() {
  }

  public static Object invokeField(ApplicationContext context, ExecuteReqBO bo) {
    Precondition.checkNotBlank(bo.getService(), "service cannot be empty.");
    Precondition.checkNotBlank(bo.getField(), "field cannot be empty.");

    Object obj = null;
    Object bean = context.getBean(bo.getService());

    //
    try {
      obj = FieldUtils.readField(bean, bo.getField(), true);
    } catch (Exception e) {
      log.warn("fail to read field", e);
    }

    return obj;
  }

  public static Object invokeMethod(ApplicationContext context, ExecuteReqBO bo) {
    String service = bo.getService();
    String method = bo.getMethod();
    //
    Precondition.checkNotBlank(service, "service cannot be empty.");
    Precondition.checkNotBlank(method, "method cannot be empty.");
    //
    Object obj = null;
    Object bean = context.getBean(service);
    Method[] methods = bean.getClass().getMethods();

    if (ArrayUtil.isEmpty(methods)) {
      log.warn("methods is empty.");
      return ReflectUtil.invokeMethod(bean, method);
    }
    //
    boolean foundFlag = false;
    int inputArgumentCount = CollectionUtils.size(bo.getArguments());
    //
    for (Method methodObj : methods) {
      // 遍历所有方法
      if (EqualUtil.isNotEq(methodObj.getName(), method)) {
        continue;
      }
      int parameterCount = methodObj.getParameterCount();
      log.info("argument count, sign={}, input={}", parameterCount, inputArgumentCount);
      if (inputArgumentCount != parameterCount) {
        continue;
      }

      foundFlag = true;
      try {
        if (parameterCount > 0) {
          List<Object> args = getInputArgumentList(bo, methodObj);
          obj = MethodUtils.invokeMethod(bean, method, args.toArray());
        } else {
          obj = MethodUtils.invokeMethod(bean, method);
        }
      } catch (Exception e) {
        log.warn("invoke method exception", e);
        throw new BaseAppException(ExceptionUtil.removeInvocation(e));
      }
      break;
    }

    if (!foundFlag) {
      log.warn("not found, {}.{} {} arguments", service, methods, inputArgumentCount);
    }

    return obj;
  }


  private static List<Object> getInputArgumentList(ExecuteReqBO bo, Method methodObj) {
    List<Object> args = new ArrayList<>();
    //
    Class<?>[] parameterTypes = methodObj.getParameterTypes();

    //
    for (int i = 0; i < bo.getArguments().size(); i++) {
      ExecuteArgumentInfoBO argumentInfoBO = bo.getArguments().get(i);
      String targetClassName = argumentInfoBO.getClassName();
      Class<?> targetClass = null;
      //
      if (StringUtil.isNotBlank(targetClassName)) {
        log.info("targetClassString is not null, so try it ");
        targetClass = ClassUtil.load(targetClassName, false);
      }
      if (Objects.isNull(targetClass)) {
        log.info("target class is null, so try to parameterTypes");
        targetClass = parameterTypes[i];
      }
      if (Objects.isNull(targetClass)) {
        log.warn("cannot refer target class");
        throw new BaseAppException("targetClass不能为空");
      }
      //
      String value = parseArgument(argumentInfoBO.getValue());
      // target class is simple String
      if (targetClass == String.class) {
        args.add(value);
      } else {
        args.add(JacksonUtil.toObject(value, targetClass));
      }
    }
    //
    return args;
  }


  private static String parseArgument(Object argumentObj) {
    String argument = "";
    //
    if (argumentObj == null) {
      return argument;
    }
    //基础类型
    if (argumentObj instanceof String) {
      String value = (String) argumentObj;
      if (StringUtil.isNotBlank(value)) {
        argument = value;
      }
    } else {
      //复杂类型
      argument = JacksonUtil.toString(argumentObj);
    }
    //
    return argument;
  }
}
