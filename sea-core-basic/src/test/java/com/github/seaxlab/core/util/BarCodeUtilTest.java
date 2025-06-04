package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/26
 * @since 1.0
 */
@Slf4j
public class BarCodeUtilTest extends BaseCoreTest {

  @Test
  public void test17() throws Exception {

    String filePath = PathUtil.getUserHome() + "/sea/core/bar-code.png";
    File file = new File(filePath);
    FileUtil.ensureDir(file.getParent());

    BarCodeUtil.encode(filePath, "978020137962");

    String content = BarCodeUtil.decode(filePath);

    Assert.assertEquals(content, "978020137962");
  }
}
