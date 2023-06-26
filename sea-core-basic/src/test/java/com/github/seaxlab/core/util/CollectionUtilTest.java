package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.domain.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/19
 * @since 1.0
 */
@Slf4j
public class CollectionUtilTest extends BaseCoreTest {

  private final List<User> users = new ArrayList<>();

  @Before
  public void before() {
    for (int i = 0; i < 5; i++) {
      User user = new User();
      user.setId(Long.parseLong(String.valueOf(i)));
      user.setAge(RandomUtil.nextInt(1, 100));
      users.add(user);
    }
    log.info("users={}", users);
  }


  @Test
  public void test17() throws Exception {
    Collection<String> all = new ArrayList<>();
    all.add("1");
    all.add("2");
    all.add("3");
    log.info("{}", CollectionUtil.toString(all));

    Collection<Integer> all2 = new ArrayList<>();
    all2.add(1);
    all2.add(2);
    all2.add(3);
    log.info("{}", CollectionUtil.toString(all2));
  }

  @Test
  public void testMin() throws Exception {
    log.info("min={}", CollectionUtil.min(users, User::getAge));
    log.info("max={}", CollectionUtil.max(users, User::getAge));
    log.info("min property={}", CollectionUtil.minProperty(users, User::getAge));
    log.info("max property={}", CollectionUtil.maxProperty(users, User::getAge));
    log.info("min and max={}", CollectionUtil.getMinAndMax(users, User::getAge));
    log.info("min and max property={}", CollectionUtil.getMinAndMaxProperty(users, User::getAge));
  }

  List<String> a = Arrays.asList("a", "b", "b");
  List<String> b = Arrays.asList("b", "b", "c", "d");
  List<String> c = Arrays.asList("f", "g", "h");

  @Test
  public void testIntersection() throws Exception {

    log.info("{}", CollectionUtil.intersection(a, b));
    Collection<?> collection = CollectionUtil.intersection(b, a);
    log.info("{}", collection);

    log.info("{}", CollectionUtil.intersection(b, c));
  }


  @Test
  public void testUnion() throws Exception {
    log.info("{}", CollectionUtil.union(a, b));
    Collection<?> collection = CollectionUtil.union(b, a);
    log.info("{}", collection);
  }

  @Test
  public void testDisjunction() throws Exception {
    log.info("{}", CollectionUtil.disjunction(a, b));
    log.info("{}", CollectionUtil.disjunction(b, a));
  }


}
