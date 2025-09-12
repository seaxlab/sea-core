package com.github.seaxlab.core.spring.component.tunnel.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.spring.component.tunnel.bo.BeanEventReqBO;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteArgumentInfoBO;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteReqBO;
import com.github.seaxlab.core.spring.component.tunnel.bo.StaticTunnelReqBO;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * tunnel util
 *
 * @author spy
 * @version 1.0 2024/08/31
 * @since 1.0
 */
@Slf4j
public final class TunnelUtil {

  private TunnelUtil() {
  }

  /**
   * 取字段值
   *
   * @param context
   * @param bo
   * @return
   */
  public static Object invokeField(ApplicationContext context, ExecuteReqBO bo) {
    Preconditions.checkNotNull(bo.getService(), "service cannot be empty.");
    Preconditions.checkNotNull(bo.getField(), "field cannot be empty.");

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

  /**
   * 调用方法
   *
   * @param context application context
   * @param bo      execute request bo
   * @return 返回值
   */
  public static Object invokeMethod(ApplicationContext context, ExecuteReqBO bo) {
    String service = bo.getService();
    String method = bo.getMethod();
    //
    Preconditions.checkNotNull(service, "service cannot be empty.");
    Preconditions.checkNotNull(method, "method cannot be empty.");
    //
    Object obj = null;
    Object bean = context.getBean(service);
    Method[] methods = bean.getClass().getMethods();

    if (ArrayUtils.isEmpty(methods)) {
      log.warn("methods is empty.");
      try {
        return MethodUtils.invokeMethod(bean, method);
      } catch (Exception e) {
        Throwable cause = removeInvocation(e);
        log.warn("invoke method error", cause);
        throw new RuntimeException(cause);
      }
    }
    boolean foundFlag = false;
    int inputArgumentCount = CollectionUtils.size(bo.getArguments());
    //
    for (Method methodObj : methods) {
      if (!StringUtils.equals(methodObj.getName(), method)) {
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
          List<Object> args = getInputArgumentList(bo.getArguments(), methodObj);
          obj = MethodUtils.invokeMethod(bean, method, args.toArray());
        } else {
          obj = MethodUtils.invokeMethod(bean, method);
        }
      } catch (Exception e) {
        Throwable cause = removeInvocation(e);
        log.warn("invoke method exception", cause);
        throw new RuntimeException(cause);
      }
      break;
    }

    if (!foundFlag) {
      log.warn("not found, {}.{} {} arguments", service, methods, inputArgumentCount);
    }

    return obj;
  }


  /**
   * invoke application event directly
   *
   * @param context
   * @param bo
   * @return
   */
  public static Object invokeBeanEvent(ApplicationContext context, BeanEventReqBO bo) {
    //
    Object obj = null;
    try {
      context.publishEvent(parseApplicationEvent(bo.getArgument()));
    } catch (Exception e) {
      Throwable cause = removeInvocation(e);
      log.warn("invoke method exception", cause);
      throw new RuntimeException(cause);
    }

    return obj;
  }

  /**
   * 调用静态方法
   *
   * @param bo
   * @return
   */
  public static Object invokeStaticMethod(StaticTunnelReqBO bo) {
    String method = bo.getMethod();
    //
    Preconditions.checkNotNull(method, "method cannot be empty.");
    //
    Object obj = null;
    Class<?> clazz = null;
    try {
      clazz = ClassUtils.getClass(bo.getClassName());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    Method[] methods = clazz.getMethods();

    if (ArrayUtils.isEmpty(methods)) {
      log.warn("methods is empty.");
      try {
        return MethodUtils.invokeStaticMethod(clazz, method);
      } catch (Exception e) {
        Throwable cause = removeInvocation(e);
        log.warn("invoke method error", cause);
        throw new RuntimeException(cause);
      }
    }
    boolean foundFlag = false;
    int inputArgumentCount = CollectionUtils.size(bo.getArguments());
    //
    for (Method methodObj : methods) {
      if (!StringUtils.equals(methodObj.getName(), method)) {
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
          List<Object> args = getInputArgumentList(bo.getArguments(), methodObj);
          obj = MethodUtils.invokeStaticMethod(clazz, method, args.toArray());
        } else {
          obj = MethodUtils.invokeStaticMethod(clazz, method);
        }
      } catch (Exception e) {
        Throwable cause = removeInvocation(e);
        log.warn("invoke method exception", cause);
        throw new RuntimeException(cause);
      }
      break;
    }

    if (!foundFlag) {
      log.warn("not found, {}.{} {} arguments", bo.getClassName(), methods, inputArgumentCount);
    }

    return obj;
  }


  public static Throwable removeInvocation(Throwable t) {
    if (t instanceof InvocationTargetException) {
      return ((InvocationTargetException) t).getTargetException();
    }

    return t;
  }


  //----------------------private------------------

  /**
   * 构造参数列表
   *
   * @param arguments execute request bo
   * @param methodObj method
   * @return all argument
   */
  private static List<Object> getInputArgumentList(List<ExecuteArgumentInfoBO> arguments, Method methodObj) {
    List<Object> args = new ArrayList<>();
    //
    Class<?>[] parameterTypes = methodObj.getParameterTypes();
    //
    for (int i = 0; i < arguments.size(); i++) {
      ExecuteArgumentInfoBO argumentInfoBO = arguments.get(i);
      String targetClassName = argumentInfoBO.getClassName();
      Class<?> targetClass = null;
      //
      if (StringUtils.isNotBlank(targetClassName)) {
        log.info("targetClassString is not null, so try it ");
        try {
          targetClass = ClassUtils.getClass(targetClassName, false);
        } catch (ClassNotFoundException e) {
          log.warn("class not found exception", e);
        }
      }
      if (Objects.isNull(targetClass)) {
        log.info("target class is null, so try to parameterTypes");
        targetClass = parameterTypes[i];
      }
      if (Objects.isNull(targetClass)) {
        log.warn("cannot refer target class");
        throw new RuntimeException("targetClass不能为空");
      }
      //
      String value = parseArgument(argumentInfoBO.getValue());
      //
      // target class is simple String
      if (targetClass == String.class) {
        args.add(value);
      } else if (targetClass == Date.class) {
        args.add(parseDateTypeValue(value));
      } else {
        args.add(JSON.parseObject(value, targetClass));
      }
    }
    //
    return args;
  }


  private static <T extends ApplicationEvent> T parseApplicationEvent(ExecuteArgumentInfoBO argumentInfoBO) {
    String targetClassName = argumentInfoBO.getClassName();
    Class<?> targetClass = null;
    //
    if (StringUtils.isNotBlank(targetClassName)) {
      log.info("targetClassString is not null, so try it ");
      try {
        targetClass = ClassUtils.getClass(targetClassName, false);
      } catch (ClassNotFoundException e) {
        log.warn("class not found exception", e);
      }
    }
    if (Objects.isNull(targetClass)) {
      log.warn("cannot refer target class");
      throw new RuntimeException("targetClass不能为空");
    }
    //
    String value = parseArgument(argumentInfoBO.getValue());
    JSONObject valueObject = JSON.parseObject(value);
    valueObject.putIfAbsent("source", "test");
    //
    // target class is simple String
    return (T) JSON.parseObject(valueObject.toJSONString(), targetClass);
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
      if (StringUtils.isNotBlank(value)) {
        argument = value;
      }
    } else {
      //复杂类型
      argument = JSON.toJSONString(argumentObj);
    }
    //
    return argument;
  }

  private static Date parseDateTypeValue(String value) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }
    //
    SimpleDateFormat sdf = null;
    if (value.length() == 19) {
      sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    } else if (value.length() == 10) {
      sdf = new SimpleDateFormat("yyyy-MM-dd");
    } else {
      log.warn("date class, but value length[{}] is invalid, it must be 10/19", value.length());
      return null;
    }

    try {
      return sdf.parse(value);
    } catch (Exception e) {
      log.warn("parse date error", e);
    }
    log.warn("use null as date type value");
    return null;
  }

}
