package com.github.seaxlab.core.spring.controller;

import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.JSONUtil;
import com.github.seaxlab.core.util.ReflectUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
   * @param params params(service,method,argument)
   * @return
   */
  @ApiOperation(value = "execute", hidden = true)
  @PostMapping("/execute")
  public Result execute(@RequestBody Map<String, Object> params) {
    log.info("try to execute, params={}", params);
    Object obj;
    try {
      String argument = "";
      Object argumentObj = params.get("argument");
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
      log.info("final argument={}", argument);

      obj = invokeMethod((String) params.get("service"), (String) params.get("method"), argument);
      if (obj instanceof Result) {
        return (Result) obj;
      }
    } catch (Exception e) {
      log.warn("fail to invoke ", e);
      return Result.failMsg(e.getMessage());
    }
    return Result.success(obj);
  }


  //-------------------------------------private
  private Object invokeMethod(String service, String method, String argument)
    throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    Precondition.checkNotBlank(service, "service cannot be empty.");
    Precondition.checkNotBlank(method, "method cannot be empty.");
    //
    Object obj;
    Object bean = ctx.getBean(service);
    Method[] methods = bean.getClass().getMethods();

    if (ArrayUtil.isNotEmpty(methods) && StringUtil.isNotEmpty(argument)) {
      Method methodObj = Arrays.stream(methods) //
        .filter(item -> EqualUtil.isEq(item.getName(), method)) //
        .findFirst().get();

      if (methodObj.getParameterCount() > 0) {
        Class<?> parameterType = methodObj.getParameterTypes()[0];
        if (JSONUtil.isSimpleValid(argument)) {
          obj = MethodUtils.invokeMethod(bean, method, JacksonUtil.toObject(argument, parameterType));
        } else {
          obj = MethodUtils.invokeMethod(bean, method, argument);
        }
      } else {
        obj = MethodUtils.invokeMethod(bean, method);
      }
    } else {
      obj = ReflectUtil.invokeMethod(bean, method);
    }
    return obj;
  }

}
