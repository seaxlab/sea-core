package com.github.spy.sea.core.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/5
 * @since 1.0
 */
@Slf4j
@Lazy(value = false)
@Configuration
public class TaskConfig {

    @Scheduled(cron = "*/10 * * * * *")
    public void queryFailTask() {
        log.info("query fail task...");
    }

    @PostConstruct
    public void init() {
        log.info("abc");
    }

}
