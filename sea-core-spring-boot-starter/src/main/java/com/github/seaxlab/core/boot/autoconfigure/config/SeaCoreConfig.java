package com.github.seaxlab.core.boot.autoconfigure.config;

import com.github.seaxlab.core.boot.autoconfigure.SeaProperties;
import com.github.seaxlab.core.spring.aop.advisor.DynamicPointcutAdvisor;
import com.github.seaxlab.core.spring.aop.interceptor.LogCostMethodInterceptor;
import com.github.seaxlab.core.spring.aop.interceptor.LogRequestMethodInterceptor;
import com.github.seaxlab.core.spring.aop.util.AopUtil;
import com.github.seaxlab.core.spring.enums.OrderedEnum;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/6
 * @since 1.0
 */
@Slf4j
@Configuration
public class SeaCoreConfig {

    private final SeaProperties seaProperties;

    public SeaCoreConfig(SeaProperties seaProperties) {
        this.seaProperties = seaProperties;
    }

    private static final String DEFAULT_EXPRESSION_LOG_COST = "@annotation(com.github.seaxlab.core.spring.annotation.LogCost)";
    private static final String DEFAULT_EXPRESSION_LOG_REQUEST = "@annotation(com.github.seaxlab.core.spring.annotation.LogRequest)";


    @Bean
    @ConditionalOnMissingBean(name = "seaLogCostAdvisor")
    public PointcutAdvisor seaLogCostAdvisor() {
        log.info("init sea log cost advisor bean");

        String expression = DEFAULT_EXPRESSION_LOG_COST;
        if (StringUtil.isNotBlank(seaProperties.getBasePackage())) {
            String condition = AopUtil.getOneExecutionByOr(seaProperties.getBasePackage());
            if (StringUtil.isNotBlank(condition)) {
                expression = MessageUtil.format("{} and {}", condition, DEFAULT_EXPRESSION_LOG_COST);
            }
        }
        DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
        advisor.setAdviceBeanName("seaLogCostPointcutAdvisor");
        advisor.setAdvice(new LogCostMethodInterceptor());
        advisor.setOrder(OrderedEnum.LOG_COST.getCode());

        return advisor;
    }


    @Bean
    @ConditionalOnMissingBean(name = "seaLogRequestAdvisor")
    public PointcutAdvisor seaLogRequestAdvisor() {
        log.info("init sea log request advisor bean");
        String expression = DEFAULT_EXPRESSION_LOG_COST;
        if (StringUtil.isNotBlank(seaProperties.getBasePackage())) {
            String condition = AopUtil.getOneExecutionByOr(seaProperties.getBasePackage());
            if (StringUtil.isNotBlank(condition)) {
                expression = MessageUtil.format("{} and {}", condition, DEFAULT_EXPRESSION_LOG_COST);
            }
        }

        DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
        advisor.setAdviceBeanName("seaLogRequestPointcutAdvisor");
        advisor.setAdvice(new LogRequestMethodInterceptor());
        advisor.setOrder(OrderedEnum.LOG_REQUEST.getCode());

        return advisor;
    }


    //private static final String DEFAULT_EXPRESSION_PROFILER = "@annotation(com.github.seaxlab.core.component.perf.anno.Profiler)";

}
