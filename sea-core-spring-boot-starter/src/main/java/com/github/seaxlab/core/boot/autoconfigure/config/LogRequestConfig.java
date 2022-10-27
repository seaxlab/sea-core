package com.github.seaxlab.core.boot.autoconfigure.config;

import com.github.seaxlab.core.boot.autoconfigure.SeaProperties;
import com.github.seaxlab.core.spring.aop.advisor.DynamicPointcutAdvisor;
import com.github.seaxlab.core.spring.aop.enums.AopExpressionEnum;
import com.github.seaxlab.core.spring.aop.interceptor.LogRequestMethodInterceptor;
import com.github.seaxlab.core.spring.aop.util.AopUtil;
import com.github.seaxlab.core.spring.enums.OrderedEnum;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * log request config
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Slf4j
@Component("seaLogRequestConfig")
@RequiredArgsConstructor
public class LogRequestConfig {

    private final SeaProperties seaProperties;
    private static final String DEFAULT_EXPRESSION_LOG_REQUEST = "@annotation(com.github.seaxlab.core.spring.annotation.LogRequest)";

    @Bean
    @ConditionalOnMissingBean(name = "seaLogRequestPointcutAdvisor")
    public PointcutAdvisor seaLogRequestAdvisor() {
        log.info("init sea log request advisor bean");
        String expression = DEFAULT_EXPRESSION_LOG_REQUEST;
        if (StringUtil.isNotBlank(seaProperties.getBasePackage())) {
            String condition = AopUtil.getByOr(AopExpressionEnum.EXECUTION_PACKAGE_AND_SUB, seaProperties.getBasePackage());
            if (StringUtil.isNotBlank(condition)) {
                expression = MessageUtil.format("{} and {}", condition, DEFAULT_EXPRESSION_LOG_REQUEST);
            }
        }

        DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
        advisor.setAdviceBeanName("seaLogRequestPointcutAdvisor");
        advisor.setAdvice(new LogRequestMethodInterceptor());
        advisor.setOrder(OrderedEnum.LOG_REQUEST.getCode());

        return advisor;
    }

}
