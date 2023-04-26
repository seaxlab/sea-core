package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.domain.User;
import com.github.seaxlab.core.thread.util.ThreadContextUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/26
 * @since 1.0
 */
@Slf4j
public class LogUtilTest extends BaseCoreTest {

  @Test
  public void printTableTest() throws Exception {
    List<String> headers = new ArrayList<>();
    headers.add("id");
    headers.add("name");
    headers.add("age");
    headers.add("isUsed");
    headers.add("isSuc");

    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId(Long.valueOf(i));
      user.setName("name" + i);
      user.setAge(i);
      user.setIsUsed(new Random().nextBoolean());
      user.setSuc(new Random().nextBoolean());
      users.add(user);
    }

    LogUtil.printTable(headers, users);
  }

  @Test
  public void test46() throws Exception {
    Exception e = new NullPointerException();
    log.error("exception in {} {}", e, "12");
  }

  @Test
  public void dumpTest() throws Exception {
    LogUtil.dump("cc", "abcd");
    sleep(10);
  }

  @Test
  public void dumpByRateTest() throws Exception {
    for (int i = 0; i < 100; i++) {
      LogUtil.dumpByRate("cc", "abcd");
    }
    sleep(10);
  }

  @Test
  public void testLogR() throws Exception {
    ThreadContextUtil.setRequestNo("10000000");
    LogUtil.debugR("this is {},{},{}", 1111, 1, 2);
    LogUtil.infoR("this is {},{}", 2222, 1);
    LogUtil.warnR("this is {},{}", 3333, 1);
    LogUtil.warnR("abc", new NullPointerException("a is null"));
    LogUtil.errorR("this is {},{}", 4444, 2);
    LogUtil.errorR("abc", new NullPointerException("b is null"));

  }


}
