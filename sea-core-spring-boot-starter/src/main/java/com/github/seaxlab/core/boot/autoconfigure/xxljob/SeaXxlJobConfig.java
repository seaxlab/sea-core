package com.github.seaxlab.core.boot.autoconfigure.xxljob;

import com.github.seaxlab.core.boot.autoconfigure.SeaProperties;
import com.github.seaxlab.core.spring.aop.advisor.DynamicPointcutAdvisor;
import com.github.seaxlab.core.spring.aop.enums.AopExpressionEnum;
import com.github.seaxlab.core.spring.aop.interceptor.ThreadContextMethodInterceptor;
import com.github.seaxlab.core.spring.aop.util.AopUtil;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * sea xxl job config
 *
 * @author spy
 * @version 1.0 2023/04/20
 * @since 1.0
 */
@Slf4j
@Configuration
@ConditionalOnClass(XxlJob.class)
@RequiredArgsConstructor
public class SeaXxlJobConfig {

  private final SeaProperties seaProperties;
  private static final String DEFAULT_EXPRESSION = "@annotation(com.xxl.job.core.handler.annotation.XxlJob)";

  @Bean
  @ConditionalOnMissingBean(name = "seaXxlJobThreadContextPointcutAdvisor")
  @ConditionalOnProperty(name = "sea.xxl-job-thread-context-enabled", havingValue = "true", matchIfMissing = true)
  public DynamicPointcutAdvisor seaScheduleThreadContextPointcutAdvisor() {
    log.info("init sea xxl-job thread context advisor bean");

    String basePackage = seaProperties.getBasePackage();

    String expression = DEFAULT_EXPRESSION;
    if (StringUtil.isNotBlank(basePackage)) {
      String condition = AopUtil.getByOr(AopExpressionEnum.EXECUTION_PACKAGE_AND_SUB, basePackage);
      if (StringUtil.isNotBlank(condition)) {
        expression = MessageUtil.format("{} and {}", condition, DEFAULT_EXPRESSION);
      }
    }

    DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
    advisor.setAdviceBeanName("seaXxlJobThreadContextPointcutAdvisor");
    advisor.setAdvice(new ThreadContextMethodInterceptor());
    advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return advisor;
  }
}
