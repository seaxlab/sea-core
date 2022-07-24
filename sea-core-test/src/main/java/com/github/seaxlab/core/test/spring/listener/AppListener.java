package com.github.seaxlab.core.test.spring.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/22
 * @since 1.0
 */
@Slf4j
@Component
public class AppListener implements ApplicationListener<ContextRefreshedEvent>, InitializingBean {

    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("refreshed event.");
    }

    public void init() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("-----");
    }
}
