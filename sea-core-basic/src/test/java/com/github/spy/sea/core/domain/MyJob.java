package com.github.spy.sea.core.domain;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/30
 * @since 1.0
 */
@Slf4j
public class MyJob implements Job {

    public void execute(JobExecutionContext jobExecutionContext) {
        log.info(new Date() + ": doing something...");
    }
}
