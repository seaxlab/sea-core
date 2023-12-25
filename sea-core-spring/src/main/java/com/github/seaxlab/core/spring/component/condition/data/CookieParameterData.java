package com.github.seaxlab.core.spring.component.condition.data;

import com.github.seaxlab.core.component.condition.data.ParameterData;
import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.loader.LoadLevel;
import java.util.List;
import org.springframework.http.HttpCookie;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * The type Cookie parameter data.
 */
@LoadLevel(name = "cookie")
public class CookieParameterData implements ParameterData {

  @Override
  public String builder(final ConditionContext context, final String paramName) {
    ServerWebExchange exchange = (ServerWebExchange) context.get("exchange");

    List<HttpCookie> cookies = exchange.getRequest().getCookies().get(paramName);
    if (CollectionUtils.isEmpty(cookies)) {
      return "";
    }
    return cookies.get(0).getValue();
  }
}
