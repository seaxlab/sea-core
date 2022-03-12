package com.github.spy.sea.core.web.filter;

import com.github.spy.sea.core.annotation.Beta;
import com.github.spy.sea.core.common.CoreErrorConst;
import com.github.spy.sea.core.model.Result;
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
 * black list filter
 *
 * @author spy
 * @version 1.0 2021/12/14
 * @since 1.0
 */
@Slf4j
@Beta
public abstract class AbstractBlackListFilter implements Filter {

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

        if (ListUtil.isNotEmpty(ips)) {
            String ip = RequestUtil.getClientIpAddress(req);
            if (EqualUtil.isIn(ip, ips)) {
                log.warn("ip[{}] is forbidden access", ip);
                Result result = Result.fail();
                result.setCode(CoreErrorConst.SYS_FORBIDDEN_ACCESS);
                result.setMsgF("ip[{}] is forbidden access", ip);
                ResponseUtil.toJSON(resp, result);
                return;
            }
        }

        if (ListUtil.isNotEmpty(userAgents)) {
            String userAgent = RequestUtil.getUserAgent(req);
            if (EqualUtil.isIn(userAgent, userAgents)) {
                log.warn("userAgent[{}] is forbidden access", userAgent);
                Result result = Result.fail();
                result.setCode(CoreErrorConst.SYS_FORBIDDEN_ACCESS);
                result.setMsg("user-agent is forbidden access");
                ResponseUtil.toJSON(resp, result);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        ips.clear();
        userAgents.clear();
    }

    abstract List<String> getIps();

    abstract List<String> getUserAgents();

}
