package com.github.spy.sea.core.spring.interceptor;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.common.Env;
import com.github.spy.sea.core.thread.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/28
 * @since 1.0
 */
@Slf4j
public class SeaMockDisableInterceptor implements HandlerInterceptor {

    @Value("${sea.env:pro}")
    private String env;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Env.PRO.equalsIgnoreCase(env)) {
            ThreadContext.remove(CoreConst.DEFAULT_MOCK_KEY);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
