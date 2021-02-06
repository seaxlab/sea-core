package com.github.spy.sea.core.boot.autoconfigure.schedule;

import com.github.spy.sea.core.boot.autoconfigure.SeaProperties;
import com.github.spy.sea.core.spring.aop.advisor.DynamicPointcutAdvisor;
import com.github.spy.sea.core.spring.aop.interceptor.ThreadContextMethodInterceptor;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SeaScheduleConfig {
    @Autowired
    private SeaProperties seaProps;

    public static final String DEFAULT_EXPRESSION_SCHEDULE = "@annotation(org.springframework.scheduling.annotation.Scheduled)";

    @Bean
    @ConditionalOnProperty(name = "sea.schedule-thread-context-enable", havingValue = "true")
    public DynamicPointcutAdvisor seaDynamicPointcutAdvisor() {
        String expression = seaProps.getScheduleThreadContextBasePackage();

        if (StringUtil.isNotEmpty(expression)) {
            expression = expression + " and " + DEFAULT_EXPRESSION_SCHEDULE;
        } else {
            expression = DEFAULT_EXPRESSION_SCHEDULE;
        }
        log.info("sea schedule thread context expression={}", expression);
        DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
        advisor.setAdviceBeanName("seaScheduleThreadContextPointcutAdvisor");
        advisor.setAdvice(new ThreadContextMethodInterceptor());
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }
}
