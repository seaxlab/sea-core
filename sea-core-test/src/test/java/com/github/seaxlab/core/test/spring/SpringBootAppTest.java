package com.github.seaxlab.core.test.spring;

import com.github.seaxlab.core.test.AbstractCoreSpringBootTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/23
 * @since 1.0
 */
@Slf4j
@SpringBootTest(classes = SampleApp.class)
public class SpringBootAppTest extends AbstractCoreSpringBootTest {

  @Test
  public void test18() throws Exception {
    log.info("ctx={}", ctx);
  }
}
