package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanMap;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/4
 * @since 1.0
 */
@Slf4j
public class BeanUtilTest extends BaseCoreTest {
  User user;

  @Before
  public void before() {
    user = new User();
    user.setId(1L);
    user.setName("abc");
  }

  @Test
  public void testBeanToMap() throws Exception {

    Map<String, Object> map = BeanMap.create(user);
    log.info("map={}", map);
    map.put("newKey", "value");
    log.info("map={}", map);
  }

  @Test
  public void testJackson() throws Exception {
    Map<String, Object> map = JacksonUtil.beanToMap(user);
    log.info("map2={}", map);
    map.put("newKey", "value");
    log.info("map={}", map);
  }
}
