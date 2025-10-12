package com.github.seaxlab.core.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域访问
 *
 * @author spy
 * @version 1.0 2019-06-25
 * @since 1.0
 */
@Slf4j
public class CorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("cors filter init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    String originHeader = ((HttpServletRequest) request).getHeader("Origin");
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;

    // 必须放在chain.doFilter之前，否则会出现header设置不生效问题
    // 若有端口需写全（协议+域名+端口）
    httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeader);
    httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,DELETE,OPTIONS,PATCH");
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", "Sea-Mock,access_token,sea-token,token,x-requested-with,Content-Type");

    chain.doFilter(request, response);
  }

}