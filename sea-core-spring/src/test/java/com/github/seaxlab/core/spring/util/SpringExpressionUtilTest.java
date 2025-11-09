package com.github.seaxlab.core.spring.util;

import com.github.seaxlab.core.spring.BaseSpringTest;
import com.github.seaxlab.core.spring.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/14
 * @since 1.0
 */
@Slf4j
public class SpringExpressionUtilTest extends BaseSpringTest {

  @Test
  public void testSimple() throws Exception {
    String content = SpringExpressionUtil.parse("name", "test", "#name", String.class);
    log.info("content={}", content);
  }

  @Test
  public void testComplexObj() throws Exception {
    User user = new User();
    user.setName("abc");

    String content = SpringExpressionUtil.parse("user", user, "#user.name+'-'+#user.code", String.class);
    log.info("content={}", content);
    content = SpringExpressionUtil.parse("user", user, "'a:b:c'+#user.name+'-'", String.class);
    log.info("content={}", content);
  }
}
