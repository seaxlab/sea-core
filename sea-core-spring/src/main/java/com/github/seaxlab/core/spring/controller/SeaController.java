package com.github.seaxlab.core.spring.controller;

import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.ClassUtil;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.ListUtil;
import com.github.seaxlab.core.util.ReflectUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sea Controller
 *
 * @author spy
 * @version 1.0 2020/9/2
 * @since 1.0
 */
@Slf4j
@RestController("seaController")
@RequestMapping("/api/sea")
@RequiredArgsConstructor
@Api(value = "SeaController", hidden = true)
public class SeaController {

  private final ApplicationContext ctx;

  @ApiOperation(value = "ping", hidden = true)
  @RequestMapping("/ping")
  public void ping(HttpServletResponse response) {
    ResponseUtil.toText(response, "pong");
  }

  /**
   * invoke public method of any bean service
   *
   * @param params params(service,method,argument1~N,argumentTypes)
   * @return result
   */
  @ApiOperation(value = "execute", hidden = true)
  @PostMapping("/execute")
  public Result<Object> execute(@RequestBody Map<String, Object> params) {
    log.info("try to execute, params={}", params);
    Object obj = null;
    try {
      String field = (String) params.get("field");
      if (StringUtil.isNotBlank(field)) {
        obj = invokeField((String) params.get("service"), field);
      } else {
        obj = invokeMethod(params);
        if (obj instanceof Result) {
          return (Result) obj;
        }
      }

    } catch (Exception e) {
      log.warn("fail to invoke ", e);
      //
      if (e instanceof InvocationTargetException) {
        InvocationTargetException ite = (InvocationTargetException) e;
        if (ite.getTargetException() instanceof BaseAppException) {
          BaseAppException be = (BaseAppException) ite.getTargetException();
          return Result.failMsg(be.getDesc());
        }
      }

      return Result.failMsg(e.getMessage());
    }
    return Result.success(obj);
  }


  //-------------------------------------private
  private Object invokeField(String service, String field) {
    Precondition.checkNotBlank(service, "service cannot be empty.");
    Precondition.checkNotBlank(field, "field cannot be empty.");

    Object obj = null;
    Object bean = ctx.getBean(service);

    //
    try {
      obj = FieldUtils.readField(bean, field, true);
    } catch (Exception e) {
      log.warn("fail to read field", e);
    }

    return obj;
  }

  private Object invokeMethod(Map<String, Object> params)
    throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    String service = (String) params.get("service");
    String method = (String) params.get("method");
    //
    Precondition.checkNotBlank(service, "service cannot be empty.");
    Precondition.checkNotBlank(method, "method cannot be empty.");
    //
    Object obj = null;
    Object bean = ctx.getBean(service);
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
      if (parameterCount > 0) {
        List<Object> args = getInputArgumentList(params, methodObj);
        obj = MethodUtils.invokeMethod(bean, method, args.toArray());
      } else {
        obj = MethodUtils.invokeMethod(bean, method);
      }
      break;
    }

    if (!foundFlag) {
      log.warn("not found, {}.{} {} arguments", service, methods, inputArgumentCount);
    }

    return obj;
  }

  private String parseArgument(Map<String, Object> params, String key) {
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

  private int getInputArgumentCount(Map<String, Object> params) {
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


  private List<Object> getInputArgumentList(Map<String, Object> params, Method methodObj) {
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
