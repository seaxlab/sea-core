package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.thread.config.CleanFileConfig;
import com.github.seaxlab.core.util.PathUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/18/20
 * @since 1.0
 */
@Slf4j
public class CleanFileThreadTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {
    String dir = PathUtil.getUserHome() + "/logs/arthas";
    CleanFileConfig config = new CleanFileConfig();
    config.setDirs(Lists.newArrayList(dir));
    config.setDelay(10);
    config.setDelayTimeUnit(TimeUnit.SECONDS);
    config.setMaxLifeTime(30);
    config.setMaxLifeTimeUnit(TimeUnit.DAYS);
    config.setFilenameFilter((dir1, name) -> {
      log.info("dir={},name={}", dir1, name);
      return false;
    });
    //
    CleanFileThread thread = new CleanFileThread(config);
    thread.start();

    sleep(1000);
  }

}
