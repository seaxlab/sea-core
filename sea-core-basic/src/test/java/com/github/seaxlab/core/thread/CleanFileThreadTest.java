package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.BaseCoreTest;
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
    String dir = getUserHome() + "/logs/arthas";
    CleanFileThread thread = new CleanFileThread(dir, 10, TimeUnit.SECONDS, 30, TimeUnit.DAYS);
    thread.setFilenameFilter((dir1, name) -> {
      log.info("dir={},name={}", dir1, name);
      return false;
    });
    thread.start();

    sleep(1000);
  }

}
