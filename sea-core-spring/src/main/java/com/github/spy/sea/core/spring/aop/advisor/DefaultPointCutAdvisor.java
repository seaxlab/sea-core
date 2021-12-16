package com.github.spy.sea.core.spring.aop.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/16
 * @since 1.0
 */
@Slf4j
public class DefaultPointCutAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private Pointcut pointcut;

    public DefaultPointCutAdvisor(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
