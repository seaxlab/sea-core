package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2024/6/21
 * @since 1.0
 */
@Slf4j
public class StreamUtilTest extends BaseCoreTest {

  @Test
  public void testToMap() throws Exception {
    List<User> data = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId(Long.parseLong("" + i));
      user.setName("name" + i);
      data.add(user);
    }

    log.info("{}", StreamUtil.toMap(data, item -> item));
  }

  @Test
  public void testToMapList() throws Exception {
    List<User> data = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId(Long.parseLong("" + i));
      user.setName("name" + i);
      data.add(user);
    }

    log.info("{}", StreamUtil.toMapList(data, item -> item));
  }


}
