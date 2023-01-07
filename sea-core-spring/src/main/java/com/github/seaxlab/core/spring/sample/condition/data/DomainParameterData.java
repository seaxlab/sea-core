package com.github.seaxlab.core.spring.sample.condition.data;

import com.github.seaxlab.core.component.condition.data.ParameterData;
import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.loader.LoadLevel;
import org.springframework.web.server.ServerWebExchange;

/**
 * The type domain parameter data.
 */
@LoadLevel(name = "domain")
public class DomainParameterData implements ParameterData {

  @Override
  public String builder(final ConditionContext context, final String paramName) {
    ServerWebExchange exchange = (ServerWebExchange) context.get("exchange");

    return exchange.getRequest().getURI().getHost();
  }
}
