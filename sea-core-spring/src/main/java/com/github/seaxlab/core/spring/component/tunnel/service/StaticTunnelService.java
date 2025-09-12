package com.github.seaxlab.core.spring.component.tunnel.service;


import com.github.seaxlab.core.spring.component.tunnel.bo.StaticTunnelReqBO;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2025/4/3
 * @since 1.0
 */
public interface StaticTunnelService {

  Object execute(StaticTunnelReqBO bo);

}
