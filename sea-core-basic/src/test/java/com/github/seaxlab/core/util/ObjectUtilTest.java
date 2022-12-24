package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/10
 * @since 1.0
 */
@Slf4j
public class ObjectUtilTest extends BaseCoreTest {

  @Test
  public void testDefaultIfNull() throws Exception {
    Boolean flag = null;
    log.info("flag={}", ObjectUtil.defaultIfNull(flag, Boolean.TRUE));
  }

  @Test
  public void testIsAllEmpty() throws Exception {
    log.info("flag={}", ObjectUtil.isAllEmpty("", null, Arrays.asList(1, 2)));
    log.info("flag={}", ObjectUtil.isAllEmpty("", null, Arrays.asList()));
    String[] strs = {};
    log.info("flag={}", ObjectUtil.isAllEmpty("", null, strs));

    String[] strs2 = {"1"};
    log.info("flag={}", ObjectUtil.isAllEmpty("", null, strs2));
  }

  @Test
  public void testProperties2Object() throws Exception {
    User user = new User();
    log.info("user={}", user);

    Properties properties = new Properties();
    properties.setProperty("age", "18");
    properties.setProperty("name", "taotao");

    ObjectUtil.properties2Object(properties, user);
    log.info("after set properties, user={}", user);
  }

  @Test
  public void testTruncateStr() throws Exception {
    User user = new User();
    user.setRemark("asdfghjkl;");

    ObjectUtil.truncateStr(user, "remark", 5);
    log.info("remark={}", user.getRemark());
  }

  @Test
  public void test2() throws Exception {
    User user = new User();
    log.info("user={}", user);
  }

}
