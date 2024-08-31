package com.github.seaxlab.core.spring.component.tunnel.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteReqBO;
import com.github.seaxlab.core.spring.component.tunnel.service.TunnelService;
import com.github.seaxlab.core.spring.component.tunnel.util.TunnelUtil;
import com.github.seaxlab.core.util.ExceptionUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2024/8/31
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultTunnelService implements TunnelService {

  private final ApplicationContext applicationContext;
  private final TransactionTemplate transactionTemplate;

  @Override
  public Result<?> execute(ExecuteReqBO bo) {
    log.info("try to execute, params={}", JSON.toJSONString(bo));
    Object obj = null;
    try {
      if (StringUtil.isNotBlank(bo.getField())) {
        obj = TunnelUtil.invokeField(applicationContext, bo);
      } else {
        //
        boolean txFlag = ObjectUtils.defaultIfNull(bo.getTxFlag(), false);
        if (txFlag) {
          //
          log.warn("execute in transaction mode");
          obj = transactionTemplate.execute(status -> TunnelUtil.invokeMethod(applicationContext, bo));
        } else {
          //
          obj = TunnelUtil.invokeMethod(applicationContext, bo);
        }
        if (obj instanceof Result) {
          return (Result<?>) obj;
        }
      }

    } catch (Exception e) {
      log.warn("fail to invoke ", e);
      //
      Throwable realException = ExceptionUtil.removeInvocation(e);
      //
      if (realException instanceof BaseAppException) {
        BaseAppException be = (BaseAppException) realException;
        //
        return Result.failMsg(StringUtil.defaultIfBlank(be.getDesc(), be.getMessage()));
      }

      return Result.failMsg(realException.getMessage());
    }
    return Result.success(obj);
  }
}
