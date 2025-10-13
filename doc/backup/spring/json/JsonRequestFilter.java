package com.github.seaxlab.core.spring.component.json;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Json request filter
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Slf4j
public class JsonRequestFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    // 用于解决controller 方法无参数时， spring 不会调用JsonParamArgumentResolver,依然可以获取请求体
    String contentType = request.getContentType();
    if (contentType != null && contentType.contains("application/json")) {
      request = new JsonRequestWrapper((HttpServletRequest) request);
    }
    chain.doFilter(request, response);
  }

}
