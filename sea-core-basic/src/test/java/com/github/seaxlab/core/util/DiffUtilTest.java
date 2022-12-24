package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.domain.Role;
import com.github.seaxlab.core.domain.User;
import com.google.common.collect.MapDifference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.diff.Diff;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public class DiffUtilTest extends BaseCoreTest {


  @Test
  public void run18() throws Exception {

    User user = new User();
    user.setId(1L);
    user.setName("abc");
    List<Role> roles = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Role role = new Role();
      role.setCode("12" + i);
      roles.add(role);
    }
    user.setRoles(roles);

    User user2 = new User();
    user2.setId(2L);
    user2.setName("abc12");

    List<Role> roles2 = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Role role = new Role();
      role.setCode("12" + i);
      roles2.add(role);
    }
    user2.setRoles(roles2);

    Diff diff = DiffUtil.compare(user, user2);

    log.info("diff={}", diff);
    diff.getChanges();
  }

  @Test
  public void testProperties() throws Exception {
    Properties p1 = new Properties();
    p1.put("a", 1);
    p1.put("b", 2);

    Properties p2 = new Properties();
    p2.put("b", 3);
    p2.put("c", 3);

    Diff diff = DiffUtil.compare(p1, p2);
    log.info("diff={}", diff);
  }


  @Test
  public void testMapDiff() throws Exception {
    String json1 = FileUtil.readFormClasspath("util/diff_json1.json");
    String json2 = FileUtil.readFormClasspath("util/diff_json2.json");

    Gson gson = new Gson();
    Type type = new TypeToken<Map<String, Object>>() {
    }.getType();

    Map<String, Object> leftMap = gson.fromJson(json1, type);
    Map<String, Object> rightMap = gson.fromJson(json2, type);

    MapDifference difference = DiffUtil.compare(leftMap, rightMap);

    System.out.println("Entries only on the left\n--------------------------");
    difference.entriesOnlyOnLeft()
              .forEach((key, value) -> System.out.println(key + ": " + value));

    System.out.println("\n\nEntries only on the right\n--------------------------");
    difference.entriesOnlyOnRight()
              .forEach((key, value) -> System.out.println(key + ": " + value));

    System.out.println("\n\nEntries differing\n--------------------------");
    difference.entriesDiffering()
              .forEach((key, value) -> System.out.println(key + ": " + value));

    System.out.println("\n\nEntries common\n--------------------------");
    difference.entriesInCommon()
              .forEach((key, value) -> System.out.println(key + ": " + value));
  }

}
