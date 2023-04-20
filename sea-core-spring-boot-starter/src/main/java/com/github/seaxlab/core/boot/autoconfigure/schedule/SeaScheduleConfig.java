package com.github.seaxlab.core.boot.autoconfigure.schedule;

import com.github.seaxlab.core.boot.autoconfigure.SeaProperties;
import com.github.seaxlab.core.spring.aop.advisor.DynamicPointcutAdvisor;
import com.github.seaxlab.core.spring.aop.enums.AopExpressionEnum;
import com.github.seaxlab.core.spring.aop.interceptor.ThreadContextMethodInterceptor;
import com.github.seaxlab.core.spring.aop.util.AopUtil;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/6
 * @since 1.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SeaScheduleConfig {

  private final SeaProperties seaProperties;
  //
  private static final String DEFAULT_EXPRESSION = "@annotation(org.springframework.scheduling.annotation.Scheduled)";

  @Bean
  @ConditionalOnMissingBean(name = "seaScheduleThreadContextPointcutAdvisor")
  @ConditionalOnProperty(name = "sea.schedule-thread-context-enabled", havingValue = "true")
  public DynamicPointcutAdvisor seaScheduleThreadContextPointcutAdvisor() {
    log.info("init sea schedule thread context advisor bean");

    String basePackage = seaProperties.getScheduleThreadContextBasePackage();

    String expression = DEFAULT_EXPRESSION;
    if (StringUtil.isNotBlank(basePackage)) {
      String condition = AopUtil.getByOr(AopExpressionEnum.EXECUTION_PACKAGE_AND_SUB, basePackage);
      if (StringUtil.isNotBlank(condition)) {
        expression = MessageUtil.format("{} and {}", condition, DEFAULT_EXPRESSION);
      }
    }

    DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
    advisor.setAdviceBeanName("seaScheduleThreadContextPointcutAdvisor");
    advisor.setAdvice(new ThreadContextMethodInterceptor());
    advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return advisor;
  }
}
