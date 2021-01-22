package com.github.spy.sea.core.example.config;

import com.github.spy.sea.core.web.servlet.WebApplicationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/19
 * @since 1.0
 */
@Slf4j
@Configuration
public class WebConfig {
//    @Bean
//    public FilterRegistrationBean seaGlobalFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new SeaGlobalFilter());
//        registration.addUrlPatterns("/api/*");
//        registration.addInitParameter("filter", "/api/,/restapi/"); //添加默认参数
//        registration.addInitParameter("noFilter", "/api/common/,/restapi/common/,/referralLogin/");
//        registration.setName("SeaGlobalFilter");
////        registration.setOrder(1);//设置优先级
//        return registration;
//    }

    @Bean
    public WebApplicationListener webApplicationListener() {
        return new WebApplicationListener();
    }
}
