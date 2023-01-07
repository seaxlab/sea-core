package com.github.seaxlab.core.boot.autoconfigure;

import com.github.seaxlab.core.spring.web.filter.SeaGlobalSpringWebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/22
 * @since 1.0
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@AutoConfigureAfter(WebFluxAutoConfiguration.class)
public class SeaCoreWebFluxAutoConfiguration {

  // so sadly
  // if web config in spring.factories, business project cannot return value in controller normally

  //TODO order、urlPattern field

  @Bean
  @ConditionalOnMissingBean(name = "seaGlobalSpringWebFilter")
  public SeaGlobalSpringWebFilter seaGlobalSpringWebFilter() {
    return new SeaGlobalSpringWebFilter();
  }

}
