package com.github.seaxlab.core.boot.autoconfigure.config;

import com.github.seaxlab.core.boot.autoconfigure.SeaProperties;
import com.github.seaxlab.core.spring.aop.advisor.DynamicPointcutAdvisor;
import com.github.seaxlab.core.spring.aop.enums.AopExpressionEnum;
import com.github.seaxlab.core.spring.aop.interceptor.LogPublicMethodInterceptor;
import com.github.seaxlab.core.spring.aop.util.AopUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * log public config
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Slf4j
@Configuration("seaLogPublicConfig")
@RequiredArgsConstructor
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LogPublicConfig {

  private final SeaProperties seaProperties;
  private final String EMPTY_PACKAGE = "com.github.seaxlab.helloworld";

  @Bean
  @ConditionalOnProperty(name = "sea.log.enabled", havingValue = "true")
  @ConditionalOnMissingBean(name = "seaLogPublicMethodPointcutAdvisor")
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public PointcutAdvisor seaLogPublicAdvisor() {
    log.info("init sea log public method advisor bean");
    String basePackage = seaProperties.getLog().getOtherBasePackage();
    if (StringUtil.isBlank(basePackage)) {
      basePackage = EMPTY_PACKAGE;
      log.warn("sea.log.otherBasePackage is empty, so set to default empty package.");
    }
    String expression = AopUtil.getByOr(AopExpressionEnum.EXECUTION_PACKAGE, basePackage);

    DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
    advisor.setAdviceBeanName("seaLogPublicMethodPointcutAdvisor");
    advisor.setAdvice(new LogPublicMethodInterceptor());

    return advisor;
  }
}
