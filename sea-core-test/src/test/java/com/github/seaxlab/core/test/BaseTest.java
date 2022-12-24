package com.github.seaxlab.core.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/14
 * @since 1.0
 */
@Slf4j
public class BaseTest extends AbstractCoreTest {

  @Test
  public void test16() throws Exception {
    for (int i = 0; i < 10; i++) {
      log.info("seq={}", nextSeq("abc"));
    }
  }

  @Test
  public void test24() throws Exception {
    Runnable runnable = () -> {
      log.info("seq={}", nextSeq("abc"));
    };

    runInMultiThread(runnable);
    sleepMinute(5);
  }

  @Test
  public void printTableTest() throws Exception {
    List<String> headers = new ArrayList<>();
    headers.add("id");
    headers.add("name");
    headers.add("age");
    headers.add("isSuperAdmin");

    List<UserInfo> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      UserInfo userInfo = new UserInfo();
      userInfo.setId(Long.valueOf(i));
      userInfo.setName("name" + i);
      userInfo.setAge(i);
      userInfo.setSuperAdmin(new Random().nextBoolean());
      users.add(userInfo);
    }

    _printTable(headers, users);
  }
}
