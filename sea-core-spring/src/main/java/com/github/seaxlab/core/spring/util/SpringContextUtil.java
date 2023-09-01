package com.github.seaxlab.core.spring.util;

import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.ClassUtil;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.ListUtil;
import com.github.seaxlab.core.util.ReflectUtil;
import com.github.seaxlab.core.util.StringUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.context.ApplicationContext;

/**
 * spring context util
 *
 * @author spy
 * @version 1.0 2023/09/01
 * @since 1.0
 */
@Slf4j
public final class SpringContextUtil {

  private SpringContextUtil() {
  }

  public static Object invokeField(ApplicationContext context, String service, String field) {
    Precondition.checkNotBlank(service, "service cannot be empty.");
    Precondition.checkNotBlank(field, "field cannot be empty.");

    Object obj = null;
    Object bean = context.getBean(service);

    //
    try {
      obj = FieldUtils.readField(bean, field, true);
    } catch (Exception e) {
      log.warn("fail to read field", e);
    }

    return obj;
  }

  public static Object invokeMethod(ApplicationContext context, Map<String, Object> params) {
    String service = (String) params.get("service");
    String method = (String) params.get("method");
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
    boolean foundFlag = false;
    int inputArgumentCount = getInputArgumentCount(params);
    //
    for (Method methodObj : methods) {
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
          List<Object> args = getInputArgumentList(params, methodObj);
          obj = MethodUtils.invokeMethod(bean, method, args.toArray());
        } else {
          obj = MethodUtils.invokeMethod(bean, method);
        }
      } catch (Exception e) {
        log.warn("invoke method exception", e);
        throw new BaseAppException(e);
      }
      break;
    }

    if (!foundFlag) {
      log.warn("not found, {}.{} {} arguments", service, methods, inputArgumentCount);
    }

    return obj;
  }

  private static String parseArgument(Map<String, Object> params, String key) {
    Object argumentObj = params.get(key);
    String argument = "";
    if (argumentObj != null) {
      if (argumentObj instanceof String) {
        String value = (String) argumentObj;
        if (StringUtil.isNotBlank(value)) {
          argument = value;
        }
      } else {
        argument = JacksonUtil.toString(argumentObj);
      }
    }
    if (log.isDebugEnabled()) {
      log.debug("{}={}", key, argument);
    }
    return argument;
  }


  private final static int MAX_ARGUMENT_SIZE = 10;

  private static int getInputArgumentCount(Map<String, Object> params) {
    int count = 0;
    for (int i = 1; i < MAX_ARGUMENT_SIZE; i++) {
      String key = "argument" + i;
      Object argumentObj = params.get(key);
      if (Objects.isNull(argumentObj)) {
        if (log.isDebugEnabled()) {
          log.debug("argument obj, {}=null, so end.", key);
        }
        break;
      }
      count++;
    }

    return count;
  }


  private static List<Object> getInputArgumentList(Map<String, Object> params, Method methodObj) {
    List<Object> args = new ArrayList<>();
    //
    List<String> argumentTypes = (List<String>) params.get("argumentTypes");
    if (Objects.isNull(argumentTypes)) {
      argumentTypes = new ArrayList<>();
    }
    Class<?>[] parameterTypes = methodObj.getParameterTypes();
    //
    for (int i = 1; i < MAX_ARGUMENT_SIZE; i++) {
      String key = "argument" + i;
      Object argumentObj = params.get(key);
      if (Objects.isNull(argumentObj)) {
        if (log.isDebugEnabled()) {
          log.debug("argument obj, {}=null, so end.", key);
        }
        break;
      }

      Class<?> targetClass = null;

      String targetClassString = ListUtil.get(argumentTypes, i - 1);
      if (StringUtil.isNotBlank(targetClassString)) {
        log.info("targetClassString is not null, so try it ");
        targetClass = ClassUtil.load(targetClassString, false);
      }
      if (Objects.isNull(targetClass)) {
        log.info("target class is null, so try to parameterTypes");
        targetClass = parameterTypes[i - 1];
      }
      if (Objects.isNull(targetClass)) {
        log.warn("cannot refer target class");
        ExceptionHandler.publishMsg("targetClass不能为空");
      }
      //
      args.add(JacksonUtil.toObject(parseArgument(params, key), targetClass));
    }

    return args;
  }

}
