package com.github.seaxlab.core.spring.component.condition.data;

import com.github.seaxlab.core.component.condition.data.ParameterData;
import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.loader.LoadLevel;
import org.springframework.web.server.ServerWebExchange;

/**
 * The type Post parameter data.
 */
@LoadLevel(name = "post")
public class PostParameterData implements ParameterData {

  @Override
  public String builder(final ConditionContext context, final String paramName) {
    ServerWebExchange exchange = (ServerWebExchange) context.get("exchange");

    return exchange.getRequest().getQueryParams().getFirst(paramName);
  }
}
