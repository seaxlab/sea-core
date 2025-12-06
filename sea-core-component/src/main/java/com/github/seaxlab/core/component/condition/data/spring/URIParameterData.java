package com.github.seaxlab.core.component.condition.data.spring;

import com.github.seaxlab.core.component.condition.data.ParameterData;
import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.loader.LoadLevel;
import org.springframework.web.server.ServerWebExchange;

/**
 * The type URI parameter data.
 */
@LoadLevel(name = "uri")
public class URIParameterData implements ParameterData {

  @Override
  public String builder(final ConditionContext context, final String paramName) {
    ServerWebExchange exchange = (ServerWebExchange) context.get("exchange");

    return exchange.getRequest().getURI().getPath();
  }
}
