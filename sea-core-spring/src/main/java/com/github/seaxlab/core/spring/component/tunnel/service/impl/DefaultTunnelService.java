package com.github.seaxlab.core.spring.component.tunnel.service.impl;

import com.github.seaxlab.core.spring.component.tunnel.bo.BeanEventReqBO;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteReqBO;
import com.github.seaxlab.core.spring.component.tunnel.service.TunnelService;
import com.github.seaxlab.core.spring.component.tunnel.util.TunnelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * default tunnel service
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
  public Object executeSimple(ExecuteReqBO bo) {
    Object value = null;
    if (StringUtils.isNotBlank(bo.getField())) {
      value = TunnelUtil.invokeField(applicationContext, bo);
    } else {
      boolean txFlag = ObjectUtils.defaultIfNull(bo.getTxFlag(), false);
      if (txFlag) {
        log.info("execute in transaction mode");
        value = transactionTemplate.execute(status -> TunnelUtil.invokeMethod(applicationContext, bo));
      } else {
        value = TunnelUtil.invokeMethod(applicationContext, bo);
      }
    }
    return value;
  }

  @Override
  public Object executeEvent(BeanEventReqBO bo) {
    Object value = null;
    //
    boolean txFlag = ObjectUtils.defaultIfNull(bo.getTxFlag(), false);
    if (txFlag) {
      log.info("execute in transaction mode");
      value = transactionTemplate.execute(status -> TunnelUtil.invokeBeanEvent(applicationContext, bo));
    } else {
      value = TunnelUtil.invokeBeanEvent(applicationContext, bo);
    }

    return value;
  }
}
