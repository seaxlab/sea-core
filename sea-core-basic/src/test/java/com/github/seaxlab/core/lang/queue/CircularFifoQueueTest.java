package com.github.seaxlab.core.lang.queue;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/30
 * @since 1.0
 */
@Slf4j
public class CircularFifoQueueTest extends BaseCoreTest {

  @Test
  public void testQueue() throws Exception {
    CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(5);

    queue.add(1);
    queue.add(2);
    queue.add(3);
    queue.add(4);
    queue.add(5);
    log.info("{}", queue);
    queue.add(6);
    log.info("{}", queue);

    queue.add(7);
    log.info("{}", queue);

    queue.add(7);
    log.info("{}", queue);

    queue.add(8);
    log.info("{}", queue);
  }
}
