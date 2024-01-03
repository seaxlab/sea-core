package com.github.seaxlab.core.example.web.config;

import com.github.seaxlab.core.example.web.filter.SignAuthFilter;
import com.github.seaxlab.core.web.servlet.WebApplicationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

  @Bean
  public FilterRegistrationBean registerMyFilter() {
    FilterRegistrationBean<SignAuthFilter> bean = new FilterRegistrationBean<>();
    bean.setOrder(1);
    bean.setFilter(new SignAuthFilter());
    // 匹配"/hello/"下面的所有url
    bean.addUrlPatterns("/api/sign/*");
    return bean;
  }


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
