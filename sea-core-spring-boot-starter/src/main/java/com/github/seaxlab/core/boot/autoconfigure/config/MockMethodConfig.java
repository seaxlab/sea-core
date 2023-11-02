package com.github.seaxlab.core.boot.autoconfigure.config;

import com.github.seaxlab.core.boot.autoconfigure.SeaProperties;
import com.github.seaxlab.core.spring.aop.advisor.DynamicPointcutAdvisor;
import com.github.seaxlab.core.spring.aop.enums.AopExpressionEnum;
import com.github.seaxlab.core.spring.aop.util.AopUtil;
import com.github.seaxlab.core.spring.component.mock.interceptor.MockMethodInterceptor;
import com.github.seaxlab.core.spring.enums.OrderedEnum;
import com.github.seaxlab.core.util.MessageUtil;
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
 * mock method config
 *
 * @author spy
 * @version 1.0 2023/11/2
 * @since 1.0
 */
@Slf4j
@Configuration("seaMockMethodConfig")
@RequiredArgsConstructor
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class MockMethodConfig {

  private final SeaProperties seaProperties;

  private static final String DEFAULT_EXPRESSION = "@annotation(com.github.seaxlab.core.spring.component.mock.annotation.MockMethod)";

  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  @ConditionalOnMissingBean(name = "seaMockMethodPointcutAdvisor")
  @ConditionalOnProperty(name = "sea.env", havingValue = "local")
  public PointcutAdvisor seaMockMethodAdvisor() {
    log.info("init sea mock method advisor bean in lock env");

    String expression = DEFAULT_EXPRESSION;
    if (StringUtil.isNotBlank(seaProperties.getBasePackage())) {
      String condition = AopUtil.getByOr(AopExpressionEnum.EXECUTION_PACKAGE_AND_SUB, seaProperties.getBasePackage());
      if (StringUtil.isNotBlank(condition)) {
        expression = MessageUtil.format("{} and {}", condition, DEFAULT_EXPRESSION);
      }
    }
    DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
    advisor.setAdviceBeanName("seaMockMethodPointcutAdvisor");
    advisor.setAdvice(new MockMethodInterceptor());
    advisor.setOrder(OrderedEnum.MOCK_METHOD.getCode());

    return advisor;
  }

}
