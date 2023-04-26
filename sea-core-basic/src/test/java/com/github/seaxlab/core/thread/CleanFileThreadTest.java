package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.thread.config.CleanFileConfig;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.javers.common.collections.Lists;
import org.junit.Test;

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
    String dir = getUserHome() + "/logs/arthas";
    CleanFileConfig config = new CleanFileConfig();
    config.setDirs(Lists.asList(dir));
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
