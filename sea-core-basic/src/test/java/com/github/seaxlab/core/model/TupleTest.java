package com.github.seaxlab.core.model;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
@Slf4j
public class TupleTest extends BaseCoreTest {

  @Test
  public void run16() throws Exception {
    Tuple tuple = new Tuple("11", "2");
    log.info("{}", tuple);
  }

  @Test
  public void ofTest() throws Exception {
    Tuple tuple = Tuple.of("11", "2");
    log.info("{}", tuple);
  }
}
