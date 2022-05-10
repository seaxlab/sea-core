package com.github.spy.sea.core.springcloud.feign.interceptor;

import com.github.spy.sea.core.springcloud.common.Const;
import com.github.spy.sea.core.thread.ThreadContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/10
 * @since 1.0
 */
@Slf4j
public class SeaFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        // 带{id}的原始url
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();
        if (request != null) {
            ThreadContext.put(Const.FEIGN_REQUEST_URI, request.getServletPath());
        }
    }
}
