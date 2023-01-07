package com.github.seaxlab.core.spring.web.util;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.http.common.MediaType;
import com.github.seaxlab.core.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;

/**
 * response util in spring mvc.
 *
 * @author spy
 * @version 1.0 2021/1/29
 * @since 1.0
 */
@Slf4j
public final class ResponseUtil {

  private ResponseUtil() {
  }

  /**
   * get response in spring mvc
   *
   * @return response
   */
  public static HttpServletResponse getResponse() {
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (servletRequestAttributes == null) {
      ExceptionHandler.publishMsg("非Web上下文无法获取Response");
    }
    return servletRequestAttributes.getResponse();
  }

  /**
   * 输出文本格式
   *
   * @param response
   * @param obj
   */
  public static Mono<Void> toText(ServerHttpResponse response, Object obj) {
    try {
      String content = obj == null ? "" : obj.toString();
      response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
      return response
        .writeWith(Mono.just(response.bufferFactory().wrap(content.getBytes())));
    } catch (Exception e) {
      log.error("response error", e);
    }
    return Mono.empty();
  }

  /**
   * 输出JSON数据
   *
   * @param response
   * @param obj
   */
  public static Mono<Void> toJSON(ServerHttpResponse response, Object obj) {
    try {
      String content = obj == null ? "" : JSONUtil.toStr(obj);
      response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
      return response
        .writeWith(Mono.just(response.bufferFactory().wrap(content.getBytes())));
    } catch (Exception e) {
      log.error("response error", e);
    }
    return Mono.empty();
  }
}
