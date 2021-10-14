package com.github.spy.sea.core.example.task;

import com.github.spy.sea.core.thread.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/5
 * @since 1.0
 */
@Slf4j
@Component
public class Demo2Task {

    @Scheduled(cron = "*/10 * * * * *")
    public void queryFailTask() {
        log.info("query fail task...");
        ThreadUtil.sleepSecond(15);
    }

}
