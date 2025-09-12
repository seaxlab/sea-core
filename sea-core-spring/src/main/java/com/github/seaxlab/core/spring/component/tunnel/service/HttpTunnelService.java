package com.github.seaxlab.core.spring.component.tunnel.service;


import com.github.seaxlab.core.spring.component.tunnel.bo.HttpTunnelReqBO;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2025/3/21
 * @since 1.0
 */
public interface HttpTunnelService {

  Object execute(HttpTunnelReqBO bo);

}
