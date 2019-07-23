package com.github.spy.sea.core.spring.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 应用启动
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
@Slf4j
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationContext ctx;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("application context refresh event");

        if (event.getApplicationContext().getParent() != null) {
            return;
        }

        log.info("=======================================");
        log.info("=         Application Ready           =");
        log.info("=======================================");

        doStatic();

    }

    private void doStatic() {
        log.info("bean definition count = {}", ctx.getBeanDefinitionCount());
    }
}
