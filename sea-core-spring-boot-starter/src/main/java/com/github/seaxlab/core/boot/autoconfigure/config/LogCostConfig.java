package com.github.seaxlab.core.boot.autoconfigure.config;

import com.github.seaxlab.core.boot.autoconfigure.SeaProperties;
import com.github.seaxlab.core.spring.aop.advisor.DynamicPointcutAdvisor;
import com.github.seaxlab.core.spring.aop.enums.AopExpressionEnum;
import com.github.seaxlab.core.spring.aop.interceptor.LogCostMethodInterceptor;
import com.github.seaxlab.core.spring.aop.util.AopUtil;
import com.github.seaxlab.core.spring.enums.OrderedEnum;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Log cost config
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Slf4j
@Configuration("seaLogCostConfig")
@RequiredArgsConstructor
public class LogCostConfig {

  private final SeaProperties seaProperties;

  private static final String DEFAULT_EXPRESSION = "@annotation(com.github.seaxlab.core.spring.annotation.LogCost)";

  @Bean
  @ConditionalOnMissingBean(name = "seaLogCostPointcutAdvisor")
  public PointcutAdvisor seaLogCostAdvisor() {
    log.info("init sea log cost advisor bean");

    String expression = DEFAULT_EXPRESSION;
    if (StringUtil.isNotBlank(seaProperties.getBasePackage())) {
      String condition = AopUtil.getByOr(AopExpressionEnum.EXECUTION_PACKAGE_AND_SUB, seaProperties.getBasePackage());
      if (StringUtil.isNotBlank(condition)) {
        expression = MessageUtil.format("{} and {}", condition, DEFAULT_EXPRESSION);
      }
    }
    DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
    advisor.setAdviceBeanName("seaLogCostPointcutAdvisor");
    advisor.setAdvice(new LogCostMethodInterceptor());
    advisor.setOrder(OrderedEnum.LOG_COST.getCode());

    return advisor;
  }
}
