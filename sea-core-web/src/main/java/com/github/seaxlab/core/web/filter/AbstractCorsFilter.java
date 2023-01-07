package com.github.seaxlab.core.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/10/14
 * @since 1.0
 */
@Slf4j
public abstract class AbstractCorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("abstract cors filter init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    String originHeader = ((HttpServletRequest) request).getHeader("Origin");
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;

    if (isTrustOrigin(originHeader)) {
      // 若有端口需写全（协议+域名+端口）
      httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeader);
      httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,DELETE,OPTIONS,PATCH");
      httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
      httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
      httpServletResponse.setHeader("Access-Control-Allow-Headers", "sea-token,token,x-requested-with,Content-Type");
    } else {
      log.error("origin={} is not trusted.", originHeader);
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }

  abstract boolean isTrustOrigin(String origin);
}