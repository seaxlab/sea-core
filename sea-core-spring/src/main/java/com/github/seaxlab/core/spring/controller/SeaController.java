package com.github.seaxlab.core.spring.controller;

import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteReqBO;
import com.github.seaxlab.core.spring.component.tunnel.service.TunnelService;
import com.github.seaxlab.core.spring.component.tunnel.service.impl.DefaultTunnelService;
import com.github.seaxlab.core.spring.tx.service.TxService;
import com.github.seaxlab.core.spring.util.SpringContextUtil;
import com.github.seaxlab.core.util.ExceptionUtil;
import com.github.seaxlab.core.util.MapUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
   * get property from env
   *
   * @param params
   * @return
   */
  @ApiOperation(value = "env/get", hidden = true)
  @PostMapping("/env/get")
  public Result<Object> getProperty(@RequestBody Map<String, Object> params) {
    Object obj = null;
    try {
      String key = (String) params.get("key");
      obj = ctx.getEnvironment().getProperty(key, "");
    } catch (Exception e) {
      log.warn("fail to invoke ", e);
      //
      Throwable targetException = ExceptionUtil.removeInvocation(e);
      if (targetException instanceof BaseAppException) {
        BaseAppException be = (BaseAppException) targetException;
        return Result.failMsg(be.getDesc());
      }

      return Result.failMsg(e.getMessage());
    }
    return Result.success(obj);
  }


  /**
   * invoke public method of any bean service
   *
   * @param params params(service,method,argument1~N,argumentTypes,txFlag)
   * @return result
   */
  @ApiOperation(value = "execute", hidden = true, notes = "service,method,argument1~N,argumentTypes,txFlag")
  @PostMapping("/execute")
  public Result<Object> execute(@RequestBody Map<String, Object> params) {
    log.info("try to execute, params={}", params);
    Object obj = null;
    try {
      String field = (String) params.get("field");
      if (StringUtil.isNotBlank(field)) {
        obj = SpringContextUtil.invokeField(ctx, (String) params.get("service"), field);
      } else {
        boolean txFlag = MapUtil.getBoolean(params, "txFlag", false);
        if (txFlag) {
          TxService txService = ctx.getBean(TxService.class);
          Precondition.checkNotNull(txService);
          txService.execute(() -> SpringContextUtil.invokeMethod(ctx, params));
        } else {
          obj = SpringContextUtil.invokeMethod(ctx, params);
        }
        if (obj instanceof Result) {
          return (Result) obj;
        }
      }

    } catch (Exception e) {
      log.warn("fail to invoke ", e);
      //
      Throwable realException = ExceptionUtil.removeInvocation(e);
      if (realException instanceof BaseAppException) {
        BaseAppException be = (BaseAppException) realException;
        //
        return Result.failMsg(StringUtil.defaultIfBlank(be.getDesc(), be.getMessage()));
      }

      return Result.failMsg(realException.getMessage());
    }
    return Result.success(obj);
  }

  //-------------------------------------private


}
