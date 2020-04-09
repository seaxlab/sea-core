package com.github.spy.sea.core.web.filter;

import com.github.spy.sea.core.thread.ThreadContext;
import com.github.spy.sea.core.web.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/23
 * @since 1.0
 */
@Slf4j
public class SeaGlobalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            logRequest((HttpServletRequest) request);
            chain.doFilter(request, response);
        } finally {
            ThreadContext.clean();
        }
    }

    @Override
    public void destroy() {

    }

    private void logRequest(HttpServletRequest request) {
        RequestUtil.logSimple(request);
    }
}
