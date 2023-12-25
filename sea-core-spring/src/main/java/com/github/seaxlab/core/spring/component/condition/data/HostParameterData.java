package com.github.seaxlab.core.spring.component.condition.data;

import com.github.seaxlab.core.component.condition.data.ParameterData;
import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.loader.LoadLevel;
import org.springframework.web.server.ServerWebExchange;

/**
 * The type Host parameter data.
 */
@LoadLevel(name = "host")
public class HostParameterData implements ParameterData {

  @Override
  public String builder(final ConditionContext context, final String paramName) {
    ServerWebExchange exchange = (ServerWebExchange) context.get("exchange");

    return exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
//        return RequestUtil.getClientIpAddress(exchange.getRequest());
  }
}
