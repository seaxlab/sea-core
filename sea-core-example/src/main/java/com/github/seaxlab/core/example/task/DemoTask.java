package com.github.seaxlab.core.example.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
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
public class DemoTask implements InitializingBean {

  //@Scheduled(cron = "*/10 * * * * *")
  //public void queryFailTask() {
  //    log.info("query fail task...");
  //    ThreadUtil.sleepSecond(25);
  //}

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("abc");
  }
}
