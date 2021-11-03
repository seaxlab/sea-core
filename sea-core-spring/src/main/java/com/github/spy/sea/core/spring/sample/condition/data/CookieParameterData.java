package com.github.spy.sea.core.spring.sample.condition.data;

import com.github.spy.sea.core.component.condition.data.ParameterData;
import com.github.spy.sea.core.component.condition.dto.ConditionContext;
import com.github.spy.sea.core.loader.LoadLevel;
import org.springframework.http.HttpCookie;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

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
