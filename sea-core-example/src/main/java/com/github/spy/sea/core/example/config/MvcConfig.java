package com.github.spy.sea.core.example.config;

import com.github.spy.sea.core.spring.interceptor.RequestLogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-09
 * @since 1.0
 */
@Slf4j
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        RequestLogInterceptor requestLogInterceptor = new RequestLogInterceptor();

        requestLogInterceptor.setIncludePayload(true);

        registry.addInterceptor(requestLogInterceptor).addPathPatterns("/api/**");


    }
}
