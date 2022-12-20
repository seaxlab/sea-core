package com.github.seaxlab.core.component.buffertrigger;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.buffertrigger.simple.SimpleBufferTrigger;
import com.github.seaxlab.core.util.RandomUtil;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * buffer trigger test
 *
 * @author spy
 * @version 1.0 2022/12/19
 * @since 1.0
 */
@Slf4j
public class SimpleBufferTriggerTest extends BaseCoreTest {

  @Test
  public void test36() throws Exception {
    BufferTrigger<Long> bufferTrigger = SimpleBufferTrigger.newBuilder()//
      .interval(2, TimeUnit.SECONDS) //
      .consumer(set -> {
//        sleepMs(700);
        log.info("set={}", set);
      }) //
      .build();

    //
    for (int i = 0; i < 50; i++) {
      bufferTrigger.enqueue(RandomUtil.nextLong(100, 200));
      sleepMs(RandomUtil.nextLong(200, 1000));
    }
    log.info("----end");
    sleepSecond(10);
  }

}
