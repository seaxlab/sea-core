package com.github.seaxlab.core.spring.interceptor;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.web.util.RequestUtil;
import com.github.seaxlab.core.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * simple token interceptor.
 *
 * @author spy
 * @version 1.0 2021/1/26
 * @since 1.0
 */
@Slf4j
public abstract class SimpleTokenInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String clientToken = RequestUtil.getParamByPriority(request, "token");

    if (EqualUtil.isEq(getServerToken(), clientToken, false)) {
      return true;
    }
    log.warn("client token[{}] is invalid", clientToken);
    Result ret = Result.failMsg("invalid request.");
    ResponseUtil.toJSON(response, ret);
    return false;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

  }

  public abstract String getServerToken();

}
