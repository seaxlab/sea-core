package com.github.seaxlab.core.boot.autoconfigure.config;

import com.github.seaxlab.core.boot.autoconfigure.SeaProperties;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.spring.aop.AopExpressionEnum;
import com.github.seaxlab.core.spring.aop.advisor.DynamicPointcutAdvisor;
import com.github.seaxlab.core.spring.aop.interceptor.LogPublicMethodInterceptor;
import com.github.seaxlab.core.spring.aop.util.AopUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * log public config
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Slf4j
@Component("seaLogPublicConfig")
@RequiredArgsConstructor
public class LogPublicConfig {

    private final SeaProperties seaProperties;

    @Bean
    @ConditionalOnProperty(name = "sea.log.enable", havingValue = "true")
    @ConditionalOnMissingBean(name = "seaLogPublicMethodPointcutAdvisor")
    public PointcutAdvisor seaLogPublicAdvisor() {
        log.info("init sea log public method advisor bean");
        String basePackage = seaProperties.getLog().getOtherBasePackage();
        Precondition.checkNotEmpty(basePackage, "sea.log.otherBasePackage cannot be empty in application.yml");
        String expression = AopUtil.getOneExecutionByOr(AopExpressionEnum.EXECUTION_PACKAGE, basePackage);

        DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(expression);
        advisor.setAdviceBeanName("seaLogPublicMethodPointcutAdvisor");
        advisor.setAdvice(new LogPublicMethodInterceptor());

        return advisor;
    }
}
