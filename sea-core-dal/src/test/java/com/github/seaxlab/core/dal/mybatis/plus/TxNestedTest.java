package com.github.seaxlab.core.dal.mybatis.plus;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/5/18
 * @since 1.0
 */
@Slf4j
public class TxNestedTest extends BasePlusTest {

  @Resource
  private User2Service user2Service;

  @Test
  public void test20() throws Exception {
    user2Service.add3();
  }

  @Test
  public void test26() throws Exception {
    user2Service.add4();
  }

}
