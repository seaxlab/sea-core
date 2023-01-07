package com.github.seaxlab.core;

import com.github.seaxlab.core.test.AbstractCoreTest;
import com.github.seaxlab.core.util.PathUtil;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * base core test
 *
 * @author spy
 * @version 1.0 2019-06-16
 * @since 1.0
 */
public class BaseCoreTest extends AbstractCoreTest {

  private Logger logger = LoggerFactory.getLogger(BaseCoreTest.class);
  // 必须要有 User-Agent
  protected String IP_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=115.204.165.147";

  protected String USER_HOME;

  @Before
  public void before() throws Exception {
    USER_HOME = getUserHome();
  }


  protected String getUserHome() {
    return PathUtil.getUserHome();
  }

  protected void sleep(int second) {
    try {
      TimeUnit.SECONDS.sleep(second);
    } catch (Exception e) {
    }
  }

  protected void println(Object obj) {
    logger.info("{}", obj);
  }

  /**
   * 获取文件流
   *
   * @param fileInClassPath
   * @return
   */
  protected InputStream getInputStream(String fileInClassPath) {
    assert fileInClassPath != null;
    InputStream inputStream = this.getClass()
                                  .getClassLoader()
                                  .getResourceAsStream(fileInClassPath);
    return inputStream;
  }

}
