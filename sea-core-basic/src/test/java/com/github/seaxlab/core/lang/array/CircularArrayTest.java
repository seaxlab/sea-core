package com.github.seaxlab.core.lang.array;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/5/5
 * @since 1.0
 */
@Slf4j
public class CircularArrayTest extends BaseCoreTest {

  @Test
  public void test17() throws Exception {
    CircularArray<String> circularArray = new CircularArray<>(10);

    for (int i = 0; i < 10; i++) {
      circularArray.addLast(String.valueOf(i));
    }

    for (String item : circularArray) {
      log.info("item={}", item);
    }

    circularArray.clear();
    log.info("======================================");

    for (int i = 0; i < 5; i++) {
      circularArray.addLast(String.valueOf(i));
    }

    for (String item : circularArray) {
      log.info("item={}", item);
    }

    circularArray.clear();
    log.info("======================================");

    for (int i = 0; i < 3; i++) {
      circularArray.addLast(String.valueOf(i));
    }

    for (String item : circularArray) {
      log.info("item={}", item);
    }

    circularArray.clear();
    log.info("======================================");

    for (int i = 0; i < 500; i++) {
      circularArray.addLast(String.valueOf(i));
    }

    for (String item : circularArray) {
      log.info("item={}", item);
    }
  }
}
