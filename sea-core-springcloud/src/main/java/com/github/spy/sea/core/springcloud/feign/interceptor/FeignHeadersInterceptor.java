package com.github.spy.sea.core.springcloud.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 默认调用feign其他服务时，不带header，此插件是添加原始header到目标feign
 *
 * @author spy
 * @version 1.0 2022/5/10
 * @since 1.0
 */
@Slf4j
public class FeignHeadersInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String keys = headerNames.nextElement();
                String values = request.getHeader(keys);
                template.header(keys, values);
            }
        }
    }
}

