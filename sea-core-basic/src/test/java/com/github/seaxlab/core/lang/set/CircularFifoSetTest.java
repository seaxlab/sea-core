package com.github.seaxlab.core.lang.set;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


/**
 * circular fifo set test
 *
 * @author spy
 * @version 1.0 2022/09/30
 * @since 1.0
 */
@Slf4j
public class CircularFifoSetTest {

  @Test
  public void test17() throws Exception {
    CircularFifoSet<Integer> set = new CircularFifoSet<>(3);

    set.add(1);
    set.add(1);
    set.add(2);
    log.info("{}", set);
    set.add(3);
    log.info("{}", set);
    set.add(4);
    log.info("{}", set);
  }
}
