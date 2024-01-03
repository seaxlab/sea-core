package com.github.seaxlab.core.example.web.task;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/20
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class XxlJobTask {

  @XxlJob("simple-xxl-job")
  public void simpleXxlJob() {
    log.info("simple xxl job execute");
  }

}
