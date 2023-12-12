package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-05
 * @since 1.0
 */
@Slf4j
public class PathUtilTest extends BaseCoreTest {

  @Test
  public void testTempDir() throws Exception {
    //io.tmpdir=/var/folders/zf/h34bgq7n195gly0fxb3p3j180000gn/T/
    log.info("io.tmpdir={}.", PathUtil.getDefaultTempDir());
    log.info("io.tmpdir={}.", PathUtil.getDefaultTempDir());
  }


  @Test
  public void testJoin() throws Exception {

//    Assert.assertEquals(PathUtil.join("/Users/smith", "//file.txt"), "/Users/smith/file.txt");
//    Assert.assertEquals(PathUtil.join("/Users/smith", "//work//", "//file.txt"), "/Users/smith/work/file.txt");
    // depends on OS system
    log.info("{}", PathUtil.join("/Users/smith", "//file.txt"));
    log.info("{}", PathUtil.join("/Users/smith", "//work//", "//file.txt"));
    log.info("{}", PathUtil.join("/a/b", "", "c", "", "d"));
  }

  @Test
  public void testJoin2() throws Exception {
    String logPath = PathUtil.join(getUserHome(), "logs", "sea", "jstack");
    log.info("logPath={}", logPath);
  }
}
