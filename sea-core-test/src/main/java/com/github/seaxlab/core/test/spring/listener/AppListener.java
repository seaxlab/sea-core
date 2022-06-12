package com.github.seaxlab.core.test.spring.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/22
 * @since 1.0
 */
@Slf4j
@Component
public class AppListener implements ApplicationListener<ContextRefreshedEvent> {

    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("refreshed event.");
    }

    @PostConstruct
    public void init() {
        log.info("-----");
    }
}
