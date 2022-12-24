package com.github.seaxlab.core.example.config;

import com.github.seaxlab.core.thread.util.ScheduledThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/14
 * @since 1.0
 */
@Slf4j
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
    scheduledTaskRegistrar.setScheduler(setTaskExecutors());
  }

  @Bean(destroyMethod = "shutdown")
  public ScheduledExecutorService setTaskExecutors() {
    return ScheduledThreadPoolUtil.get("sea-task-pool", 2);
  }
}
