package com.github.seaxlab.core.spring.sample.condition.data;

import com.github.seaxlab.core.component.condition.data.ParameterData;
import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.loader.LoadLevel;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * The type Header parameter data.
 */
@LoadLevel(name = "header")
public class HeaderParameterData implements ParameterData {

  @Override
  public String builder(final ConditionContext context, final String paramName) {
    ServerWebExchange exchange = (ServerWebExchange) context.get("exchange");

    List<String> headers = exchange.getRequest().getHeaders().get(paramName);
    if (CollectionUtils.isEmpty(headers)) {
      return "";
    }
    return headers.get(0);
  }
}
