package com.github.seaxlab.core.spring.component.tunnel.service;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteReqBO;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2024/8/31
 * @since 1.0
 */
public interface TunnelService {

  Result<?> execute(ExecuteReqBO bo);
}
