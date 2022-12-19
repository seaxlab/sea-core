package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/13
 * @since 1.0
 */
@Slf4j
public class PlaceHolderUtilTest extends BaseCoreTest {

  @Test
  public void run16() throws Exception {
    Properties p = new Properties();
    p.setProperty("a", "1");

    log.info("final value = {}", PlaceholderUtil.INSTANCE.replace("${a},${b:hao}", p));
  }

  @Test
  public void testReplace() throws Exception {
    Map<String, String> param = new HashMap<>();
    param.put("a", "1");
    log.info("final value = {}", PlaceholderUtil.INSTANCE.replace("${a},${b:hao}", param));
  }

  @Test
  public void testFixNull() throws Exception {
    Map<String, String> param = new HashMap<>();
    param.put("a", null);
    log.info("final value = {}", PlaceholderUtil.INSTANCE.replace("${a}", param));
  }
}
