package com.github.seaxlab.core.spring.controller;

import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.spring.component.invoke.dto.InvokeReqDTO;
import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.JSONUtil;
import com.github.seaxlab.core.util.ReflectUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.web.util.ResponseUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
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
public class SeaController {

  private final ApplicationContext ctx;

  @RequestMapping("/ping")
  public void ping(HttpServletResponse response) {
    ResponseUtil.toText(response, "pong");
  }

  /**
   * invoke public method of any bean service
   *
   * @param dto
   * @return
   */
  @PostMapping("/execute")
  public Result execute(@RequestBody InvokeReqDTO dto) {
    log.info("try to execute simple dto={}", dto);
    Object obj = null;

    try {
      Object bean = ctx.getBean(dto.getService());
      Method[] methods = bean.getClass().getMethods();

      if (ArrayUtil.isNotEmpty(methods) && StringUtil.isNotEmpty(dto.getArgument())) {
        Method methodObj = Arrays.stream(methods) //
          .filter(item -> EqualUtil.isEq(item.getName(), dto.getMethod())) //
          .findFirst().get();

        if (methodObj.getParameterCount() > 0) {
          Class<?> parameterType = methodObj.getParameterTypes()[0];
          if (JSONUtil.isSimpleValid(dto.getArgument())) {
            obj = MethodUtils.invokeMethod(bean, dto.getMethod(),
              JacksonUtil.toObject(dto.getArgument(), parameterType));
          } else {
            obj = MethodUtils.invokeMethod(bean, dto.getMethod(), dto.getArgument());
          }
        } else {
          obj = MethodUtils.invokeMethod(bean, dto.getMethod(), dto.getArgument());
        }
      } else {
        obj = ReflectUtil.invokeMethod(bean, dto.getMethod());
      }

      if (obj instanceof Result) {
        return (Result) obj;
      }
    } catch (Exception e) {
      log.error("fail to invoke ", e);
      return Result.failMsg(e.getMessage());
    }
    return Result.success(obj);
  }

}
