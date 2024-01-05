package com.github.seaxlab.core.web.filter;

import com.github.seaxlab.core.web.filter.request.XssHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * xss filter
 *
 * @author spy
 * @version 1.0 2019-08-05
 * @since 1.0
 */
@Slf4j
public class XssFilter extends BaseWebFilter implements Filter {


  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("xss filter init");
    initExcludeConfig(filterConfig);

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    if (isExcludePath(req.getRequestURI())) {
      chain.doFilter(request, response);
      return;
    }

    // do xss filter
    XssHttpServletRequestWrapper wrapper = new XssHttpServletRequestWrapper(req);
    chain.doFilter(wrapper, response);
  }

  @Override
  public void destroy() {
    //no-op
    log.info("xss filter destroy");
  }
}
