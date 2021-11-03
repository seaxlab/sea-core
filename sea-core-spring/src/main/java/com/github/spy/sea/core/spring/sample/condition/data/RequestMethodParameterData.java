package com.github.spy.sea.core.spring.sample.condition.data;


import com.github.spy.sea.core.component.condition.data.ParameterData;
import com.github.spy.sea.core.component.condition.dto.ConditionContext;
import com.github.spy.sea.core.loader.LoadLevel;
import org.springframework.web.server.ServerWebExchange;

/**
 * The type Query parameter data.
 */
@LoadLevel(name = "requestMethod")
public class RequestMethodParameterData implements ParameterData {

    @Override
    public String builder(final ConditionContext context, final String paramName) {
        ServerWebExchange exchange = (ServerWebExchange) context.get("exchange");
        return exchange.getRequest().getMethodValue();
    }
}
