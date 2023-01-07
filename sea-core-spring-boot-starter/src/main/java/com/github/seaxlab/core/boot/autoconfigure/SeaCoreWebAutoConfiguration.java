package com.github.seaxlab.core.boot.autoconfigure;

import com.github.seaxlab.core.util.ObjectUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.web.common.WebConst;
import com.github.seaxlab.core.web.filter.SeaGlobalFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@RequiredArgsConstructor
public class SeaCoreWebAutoConfiguration implements WebMvcConfigurer {

  private final SeaProperties seaProperties;

  @Bean
  @ConditionalOnProperty(name = "sea.web.filter.enabled", havingValue = "true")
  @ConditionalOnMissingBean(name = "seaGlobalFilter")
  public FilterRegistrationBean seaGlobalFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new SeaGlobalFilter());

    SeaProperties.Filter filter = seaProperties.getWeb().getFilter();

    String[] urlPatterns;
    if (StringUtil.isNotBlank(filter.getUrlPattern())) {
      urlPatterns = StringUtil.split(filter.getUrlPattern(), ',');
    } else {
      urlPatterns = new String[]{"/api/*"};
    }
    registration.addUrlPatterns(urlPatterns);

    if (StringUtil.isNotBlank(filter.getLogMode())) {
      registration.addInitParameter(WebConst.FILTER_CONFIG_LOG_MODE, filter.getLogMode().trim());
    }

    //        registration.addInitParameter("filter", "/api/,/restapi/"); //添加默认参数
//        registration.addInitParameter("noFilter", "");
    registration.setName("SeaGlobalFilter");
    if (ObjectUtil.isNotNull(filter.getOrder())) {
      registration.setOrder(filter.getOrder());//设置优先级
    }
    return registration;
  }

//    @Bean
//    @ConditionalOnClass(SeaMockDisableInterceptor.class)
//    @ConditionalOnMissingBean(name = "seaMockDisableInterceptor")
//    public SeaMockDisableInterceptor seaMockDisableInterceptor() {
//        return new SeaMockDisableInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(seaMockDisableInterceptor())
//                .order(1000)
//                .addPathPatterns("/*");
//    }
}
