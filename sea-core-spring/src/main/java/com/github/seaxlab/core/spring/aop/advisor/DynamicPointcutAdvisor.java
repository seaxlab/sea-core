package com.github.seaxlab.core.spring.aop.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/6
 * @since 1.0
 */
@Slf4j
public class DynamicPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 表达式
     */
    private String expression;

    public DynamicPointcutAdvisor(String expression) {
        this.expression = expression;
    }

    public Pointcut aspectJExpressionPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(this.expression);
        return pointcut;
    }

    @Override
    public Pointcut getPointcut() {
        return aspectJExpressionPointcut();
    }

}
