package com.github.spy.sea.core.web.filter;

import com.github.spy.sea.core.annotation.Beta;
import com.github.spy.sea.core.common.CoreErrorConst;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.ListUtil;
import com.github.spy.sea.core.web.util.RequestUtil;
import com.github.spy.sea.core.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * white list filter
 *
 * @author spy
 * @version 1.0 2021/12/14
 * @since 1.0
 */
@Slf4j
@Beta
public abstract class AbstractWhiteListFilter implements Filter {

    private List<String> ips;
    private List<String> userAgents;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ips = getIps();
        userAgents = getUserAgents();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        boolean access = false;

        String ip = RequestUtil.getClientIpAddress(req);
        if (ListUtil.isNotEmpty(ips)) {
            access = EqualUtil.isIn(ip, ips);
        }

        if (access) {
            log.info("ip={} access system", ip);
            chain.doFilter(request, response);
            return;
        }

        String userAgent = RequestUtil.getUserAgent(req);
        if (ListUtil.isNotEmpty(userAgents)) {
            access = EqualUtil.isIn(userAgent, userAgents);
        }

        if (access) {
            log.info("user agent={} access system", userAgent);
            chain.doFilter(request, response);
            return;
        }

        log.warn("client[ip={}, client={}] is forbidden access", ip, userAgent);
        BaseResult result = BaseResult.fail();
        result.setErrorCode(CoreErrorConst.SYS_FORBIDDEN_ACCESS);
        result.setErrorMsg("client is forbidden access, plz contact administrator.");
        ResponseUtil.toJSON(resp, result);
    }

    @Override
    public void destroy() {
        ips.clear();
        userAgents.clear();
    }

    abstract List<String> getIps();

    abstract List<String> getUserAgents();
}
