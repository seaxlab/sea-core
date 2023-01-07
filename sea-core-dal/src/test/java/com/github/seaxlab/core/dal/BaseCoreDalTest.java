package com.github.seaxlab.core.dal;

import com.github.seaxlab.core.test.AbstractCoreTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/19
 * @since 1.0
 */
public abstract class BaseCoreDalTest extends AbstractCoreTest {
  private Logger logger = LoggerFactory.getLogger(BaseCoreDalTest.class);

  @Test
  public void test17() throws Exception {
    logger.info("a");
  }
}
