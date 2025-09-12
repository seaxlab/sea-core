package com.github.seaxlab.core.spring.component.tunnel.service.impl;

import com.github.seaxlab.core.spring.component.tunnel.bo.StaticTunnelReqBO;
import com.github.seaxlab.core.spring.component.tunnel.service.StaticTunnelService;
import com.github.seaxlab.core.spring.component.tunnel.util.TunnelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * default static tunnel service
 *
 * @author spy
 * @version 1.0 2025/4/3
 * @since 1.0
 */
@Slf4j
public class DefaultStaticTunnelService implements StaticTunnelService {

  @Override
  public Object execute(StaticTunnelReqBO bo) {
    Object value;

    if (StringUtils.isNotBlank(bo.getField())) {
      try {
        value = FieldUtils.readStaticField(ClassUtils.getClass(bo.getClassName()), bo.getField(), true);
      } catch (ClassNotFoundException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    } else {
      value = TunnelUtil.invokeStaticMethod(bo);
    }

    return value;
  }


}
