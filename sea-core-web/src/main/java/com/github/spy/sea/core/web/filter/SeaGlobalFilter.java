package com.github.spy.sea.core.web.filter;

import com.github.spy.sea.core.thread.ThreadContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
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
            chain.doFilter(request, response);
        } finally {
            ThreadContext.clean();
        }
    }

    @Override
    public void destroy() {

    }
}
