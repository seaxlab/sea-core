package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/26
 * @since 1.0
 */
@Slf4j
public class PathTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {
    // web app root.
    String path = new File(".").getCanonicalPath();
    // /Users/xxx/xx/sea-core/sea-core-basic
    log.info("path={}", path);
  }
}
