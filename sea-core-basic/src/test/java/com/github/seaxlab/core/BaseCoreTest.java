package com.github.seaxlab.core;

import com.github.seaxlab.core.test.AbstractCoreTest;

import java.util.concurrent.TimeUnit;

/**
 * base core test
 *
 * @author spy
 * @version 1.0 2019-06-16
 * @since 1.0
 */
public class BaseCoreTest extends AbstractCoreTest {


  protected void sleep(int second) {
    try {
      TimeUnit.SECONDS.sleep(second);
    } catch (Exception e) {
    }
  }

}
