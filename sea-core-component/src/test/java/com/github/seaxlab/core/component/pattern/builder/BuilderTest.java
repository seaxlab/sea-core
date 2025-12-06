package com.github.seaxlab.core.component.pattern.builder;

import com.github.seaxlab.core.component.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ThreadFactory;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/5
 * @since 1.0
 */
@Slf4j
public class BuilderTest extends BaseCoreTest {

  @Test
  public void test17() throws Exception {

    // builder 中设置属性不推荐使用set方法
    ThreadFactory threadFactory = ThreadFactoryBuilder.create()
                                                      .setNamePrefix("abc")
                                                      .setDaemon(true)
                                                      .build();

    log.info("thread factory={}", threadFactory);
  }
}
