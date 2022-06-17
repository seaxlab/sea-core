package com.github.seaxlab.core.domain;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/30
 * @since 1.0
 */
@Slf4j
@DisallowConcurrentExecution
public class MyJob implements Job {

    public void execute(JobExecutionContext jobExecutionContext) {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        log.info("job={},doing something.", jobDetail.getJobDataMap().getString("jobId"));
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {

        }
    }
}
