package com.github.seaxlab.core.support.oss;

import com.github.seaxlab.core.support.BaseSupportTest;
import com.github.seaxlab.core.support.oss.manager.OssManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Slf4j
public class BaseOssManagerTest extends BaseSupportTest {

  protected String ENDPOINT = "";
  protected String ACCESS_KEY = "";
  protected String SECRET_KEY = "";

  protected String BUCKET = "test-b-001";

  protected OssManager ossManager;


  @Before
  public void before() {

  }

}
