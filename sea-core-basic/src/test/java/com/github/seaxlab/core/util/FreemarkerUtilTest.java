package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */
@Slf4j
public class FreemarkerUtilTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {
    Map<String, Object> params = new HashMap<>();
    params.put("user", "smith");
    params.put("age", 10);

    log.info(FreemarkerUtil.replace("hello ${user}", params));
    log.info(FreemarkerUtil.replace("hello ${user},${age}", params));
    //TODO 注意这里的null值
    log.info(FreemarkerUtil.replace("hello ${user},${age},${(abc)!}", params));
    log.info(FreemarkerUtil.replace("hello ${user},${age},${(object.attribute)!'default text'}", params));
  }

  @Test
  public void testManually() throws Exception {
    FreemarkerUtil.initConfig(FreemarkerUtilTest.class.getClassLoader(), "/");
    Map<String, Object> params = new HashMap<>();
    params.put("name", "tt");
    params.put("remark", null);
    params.put("remark2", "");
    params.put("remark3", "remark3");
    String page = FreemarkerUtil.render("template/test.ftl", params);
    log.info("page={}", page);
  }

  @Test
  public void testManually2() throws Exception {
    FreemarkerUtil.initConfig(FreemarkerUtilTest.class.getClassLoader(), "/");

    //
    Map<String, String> tags = new HashMap<>();
    tags.put("key1", "1");
    tags.put("key2", "2");
    tags.put("key3", "3");

    Map<String, Object> params = new HashMap<>();
    params.put("tags", tags);
    String page = FreemarkerUtil.render("template/test2.ftl", params);
    log.info("page={}", page);
  }

}
