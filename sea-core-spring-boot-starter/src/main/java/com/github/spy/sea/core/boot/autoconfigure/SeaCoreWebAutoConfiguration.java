package com.github.spy.sea.core.boot.autoconfigure;

import com.github.spy.sea.core.web.filter.SeaGlobalFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/22
 * @since 1.0
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SeaCoreWebAutoConfiguration implements WebMvcConfigurer {

    //TODO order、urlPattern field

    @Bean
    @ConditionalOnMissingBean(name = "seaGlobalFilter")
    public FilterRegistrationBean seaGlobalFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SeaGlobalFilter());
        registration.addUrlPatterns("/*");
//        registration.addInitParameter("filter", "/api/,/restapi/"); //添加默认参数
//        registration.addInitParameter("noFilter", "");
        registration.setName("SeaGlobalFilter");
//        registration.setOrder(1);//设置优先级
        return registration;
    }

}
