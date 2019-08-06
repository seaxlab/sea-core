package com.github.spy.sea.core.boot.autoconfigure.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 初始化监听
 *
 * @author spy
 * @version 2019-07-23
 */
@Slf4j
public class ApplicationInitListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AbstractApplicationContext ctx;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("=======================================");
        log.info("=         Application Ready           =");
        log.info("=======================================");

        log.info("Power by Sea Framework.");

        doStatic();
    }

    private void doStatic() {
        log.info("bean definition count = {}", ctx.getBeanDefinitionCount());
    }

}
