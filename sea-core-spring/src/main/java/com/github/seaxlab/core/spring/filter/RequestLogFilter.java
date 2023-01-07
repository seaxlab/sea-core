package com.github.seaxlab.core.spring.filter;

import com.github.seaxlab.core.config.ConfigurationFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * request log filter
 *
 * @author spy
 * @version 1.0 2019-08-09
 * @since 1.0
 */
@Slf4j
public class RequestLogFilter extends CommonsRequestLoggingFilter {

  @Override
  protected boolean shouldLog(HttpServletRequest request) {
    return ConfigurationFactory.getInstance().getBoolean("sea.spring.request.log", true);
  }


  @Override
  protected void beforeRequest(HttpServletRequest request, String message) {
    log.info(message);
  }

  @Override
  protected void afterRequest(HttpServletRequest request, String message) {
//   do nothing.
//        log.info(message);
  }

  @Override
  protected boolean isIncludeClientInfo() {
    return true;
  }

  @Override
  protected int getMaxPayloadLength() {
    return 10000;
  }

  @Override
  protected boolean isIncludePayload() {
    return true;
  }

  @Override
  protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
    StringBuilder msg = new StringBuilder();
    msg.append("\n").append(prefix);
    msg.append("uri=").append(request.getRequestURI());
    msg.append(";http method=").append(request.getMethod());
    msg.append(";client=").append(request.getRemoteAddr());

    if (isIncludeQueryString()) {
      String queryString = request.getQueryString();
      if (queryString != null) {
        msg.append('?').append(queryString);
      }
    }

    if (isIncludeClientInfo()) {
      String client = request.getRemoteAddr();
      if (StringUtils.hasLength(client)) {
        msg.append(";client=").append(client);
      }
      HttpSession session = request.getSession(false);
      if (session != null) {
        msg.append(";session=").append(session.getId());
      }
      String user = request.getRemoteUser();
      if (user != null) {
        msg.append(";user=").append(user);
      }
    }

    if (isIncludeHeaders()) {
      msg.append(";headers=").append(new ServletServerHttpRequest(request).getHeaders());
    }

    if (isIncludePayload()) {
      String payload = getMessagePayload(request);
      if (payload != null) {
        msg.append(";payload=").append(payload);
      }
    }

    msg.append(suffix);
    return msg.toString();
  }
}
