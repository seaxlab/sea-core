package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/13
 * @since 1.0
 */
@Slf4j
public class TemplateUtilTest extends BaseCoreTest {

  @Test
  public void testReplace() throws Exception {
    Map<String, String> data = new HashMap<>();
    data.put("name", "Mark");
    Assert.assertEquals("hi Mark!", TemplateUtil.replace("hi {name}!", data));
  }
}
