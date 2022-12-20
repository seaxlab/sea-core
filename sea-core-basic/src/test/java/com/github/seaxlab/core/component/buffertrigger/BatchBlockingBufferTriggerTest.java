package com.github.seaxlab.core.component.buffertrigger;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import com.github.seaxlab.core.BaseCoreTest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/12/20
 * @since 1.0
 */
@Slf4j
public class BatchBlockingBufferTriggerTest extends BaseCoreTest {

  private volatile boolean check = true;
  private volatile boolean failed;
  private final BufferTrigger<Integer> trigger = BufferTrigger.<Integer>batchBlocking()//
    .batchSize(100) //
    .bufferSize(1000) //
    .setConsumerEx(this::consumer) //
    .linger(3, SECONDS).build();

  @Test
  public void test() {
    check = false;
    for (int i = 0; i < 99; i++) {
      trigger.enqueue(i);
    }
    sleepUninterruptibly(3100, MILLISECONDS);
    check = true;
    for (int i = 0; i < 190; i++) {
      trigger.enqueue(i);
    }
    sleepUninterruptibly(3100, MILLISECONDS);
    check = false;
    for (int i = 0; i < 20; i++) {
      trigger.enqueue(i);
    }
    sleepUninterruptibly(5, SECONDS);
    Assert.assertFalse(failed);
  }

  private void consumer(List<Integer> strings) {
    if (check) {
      if (strings.size() < 100) {
        failed = true;
      }
    }
    log.info("size:{}", strings.size());
    sleepUninterruptibly(1, SECONDS);
  }

}
