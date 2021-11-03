package com.github.spy.sea.core.spring.sample.condition.data;

import com.github.spy.sea.core.component.condition.data.ParameterData;
import com.github.spy.sea.core.component.condition.dto.ConditionContext;
import com.github.spy.sea.core.loader.LoadLevel;
import com.github.spy.sea.core.spring.web.util.RequestUtil;
import org.springframework.web.server.ServerWebExchange;

/**
 * The type Ip parameter data.
 */
@LoadLevel(name = "ip")
public class IpParameterData implements ParameterData {

    @Override
    public String builder(final ConditionContext context, final String paramName) {
        ServerWebExchange exchange = (ServerWebExchange) context.get("exchange");
        return RequestUtil.getClientIpAddress(exchange.getRequest());
//        return HostAddressUtils.acquireIp(exchange);
    }
}
