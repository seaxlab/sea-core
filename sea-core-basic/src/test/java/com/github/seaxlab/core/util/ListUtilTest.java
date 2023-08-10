package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.domain.Role;
import com.github.seaxlab.core.domain.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/21
 * @since 1.0
 */
@Slf4j
public class ListUtilTest extends BaseCoreTest {

  @Test
  public void testToList() throws Exception {
    String[] array = new String[]{"1", "s"};

    log.debug("{}", ListUtil.toList(array));
  }

  @Test
  public void testToStrList() throws Exception {
    String str = "1,,2,3,";
    log.info("{}", ListUtil.toStrList(str));

  }

  @Test
  public void testToIntList() throws Exception {
    String str = "1,,2,3,";
    log.info("{}", ListUtil.toIntList(str));
  }


  @Test
  public void testList() throws Exception {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);

    List<Integer> removedList = new ArrayList<>();
    removedList.add(2);
    removedList.add(3);

    list.removeAll(removedList);
    log.info("{}", list);

  }


  @Test
  public void test30() throws Exception {
    User user1 = new User();
    user1.setId(1L);
    User user2 = new User();
    user2.setId(2L);
    log.info("{}", ListUtil.newArrayList(user1, user2));
  }


  @Test
  public void testRemove1() throws Exception {
    List<String> list = new ArrayList<>();
    list.add("a");
    list.add("b");
    list.add("b");
    list.add("b");

    ListUtil.remove(list, "b");
    log.info("{}", list);
  }

  @Test
  public void testRemove() throws Exception {

    // 不能使用 Arrays.asList();
    List<String> list = new ArrayList<>();
    list.add("a");
    list.add("b");

    Predicate<String> predicate = p -> p.equalsIgnoreCase("a");

    log.info("list={}", list);
    ListUtil.delete(list, predicate);

    log.info("list={}", list);
  }

  @Test
  public void run46() throws Exception {
    List<String> list = new ArrayList<>();
    list.add("a");
    list.add("b");

    Predicate<String> predicate = p -> p.equalsIgnoreCase("a");

    list.removeIf(predicate);
    log.info("list={}", list);
  }

  @Test
  public void run58() throws Exception {
    Set<String> set = new HashSet<>();

    set.add("a");
    set.add("b");
    log.info("set={}", set);
    log.info("list={}", ListUtil.toList(set));

  }

  @Test
  public void testAdd() throws Exception {
    List<String> list1 = new ArrayList<>();
    list1.add("a");
    list1.add("b");

    List<String> list2 = new ArrayList<>();
    list2.add("a2");
    list2.add("b2");

    List<String> list3 = ListUtil.add(list1, list2);
    log.info("list1={}", list1);
    log.info("list2={}", list2);
    log.info("list3={}", list3);

  }

  @Test
  public void testToMap() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) i);
      user.setName("n" + i);
      users.add(user);
    }

    Map<Long, User> userMap = ListUtil.toMap(users, User::getId);
    log.info("{}", userMap);
  }

  @Test
  public void testToMapNew() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) i);
      user.setName("n" + i);
      users.add(user);
    }

    Map<Long, String> userMap = ListUtil.toMapNew(users, User::getId, User::getName);
    log.info("{}", userMap);
  }


  @Test
  public void testToMapBinaryOperator() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) i);
      user.setName("n" + i);
      user.setCreateTime(new Date());

      users.add(user);
    }
    sleepSecond(1);
    User user = new User();
    user.setId((long) 0);
    user.setName("n0" + 0);
    user.setCreateTime(new Date());
    users.add(user);

    Map<Long, User> userMap = ListUtil.toMap(users, item -> item.getId(), (o1, o2) -> {
      if (o1.getCreateTime().getTime() < o2.getCreateTime().getTime()) {
        log.info("----");
        return o2;
      }
      return o1;
    });
    log.info("{}", userMap);
  }

  @Test
  public void testToMap2Error() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) i);
      user.setName("n" + i);
      users.add(user);
    }
    User user = new User();
    user.setId((long) 0);
    user.setName("n" + 0);
    users.add(user);

    Map<Long, User> userMap = ListUtil.toMap(users, item -> item.getId());
    log.info("{}", userMap);
    //java.lang.IllegalStateException: Duplicate key User(id=0, name=n0, age=0, roles=null, roleCodes=null, isSuc=false, isUsed=null, group=null)
  }

  @Test
  public void testToDistinctMap() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) i);
      user.setName("n" + i);
      users.add(user);
    }
    User user = new User();
    user.setId((long) 0);
    user.setName("n" + 0);
    users.add(user);

    Map<Long, User> userMap = ListUtil.toDistinctMap(users, item -> item.getId());
    log.info("{}", userMap);
  }


  @Test
  public void toMapListTest() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) (i % 3));
      user.setName("n" + i);
      users.add(user);
    }

    Map<Long, List<User>> userMap = ListUtil.toMapList(users, user -> user.getId());
    log.info("{}", userMap);
  }

  @Test
  public void testToMapList2() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) (i % 3));
      user.setName("n" + i);
      users.add(user);
    }

    Map<Long, List<String>> userMap = ListUtil.toMapList(users, user -> user.getId(), user -> user.getName());
    log.info("{}", userMap);
    // {0=[n0, n3, n6, n9], 1=[n1, n4, n7], 2=[n2, n5, n8]}
    Assert.assertEquals(userMap.get(0L).size(), 4);
  }

  @Test
  public void testToMapCount() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) (i % 3));
      user.setName("n" + i);
      users.add(user);
    }

    log.info("users={}", users);
    Map<Long, Long> userMap = ListUtil.toMapCount(users, user -> user.getId());
    log.info("userMap={}", userMap);
  }


  @Test
  public void testToMapFlatList() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) (i % 3));
      user.setName("n" + i);

      List<String> codes = new ArrayList<>();
      codes.add("role1-" + i);
      codes.add("role2-" + i);
      user.setRoleCodes(codes);

      users.add(user);
    }

    Map<Long, List<String>> userMap = ListUtil.toMapFlatList(users, User::getId, user -> user.getRoleCodes().stream());

    log.info("{}", userMap);
  }

  @Test
  public void testToMapFlatList2() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId((long) (i % 3));
      user.setName("n" + i);

      List<String> codes = new ArrayList<>();
      codes.add("role1-" + i);
      codes.add("role2-" + i);
      user.setRoleCodes(codes);

      users.add(user);
    }
    log.info("users={}", users);

    Map<Long, List<String>> userMap = ListUtil.toMapFlatList2(users, User::getId, user -> user.getRoleCodes());
    log.info("{}", userMap);
  }


  @Test
  public void testToFlatList() throws Exception {

    List<List<User>> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      List<User> users = new ArrayList<>();
      for (int j = 0; j < 10; j++) {
        User user = new User();
        user.setId(Long.valueOf(i % 3));
        user.setName("n" + i + "_" + j);
        users.add(user);
      }
      list.add(users);
    }

    List<User> data = ListUtil.toFlatList(list);
    log.info("flat list size={}", data.size());
    log.info("{}", data);
  }


  @Test
  public void testToFlatList2() throws Exception {
    List<User> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId(Long.valueOf(i));

      List<Role> roles = new ArrayList<>();
      for (int j = 0; j < 10; j++) {
        Role role = new Role();
        role.setName("role-n" + i + "_" + j);
        roles.add(role);
      }
      user.setRoles(roles);
      list.add(user);
    }

    List<String> data = ListUtil.toFlatList(list, user -> ListUtil.toList(user.getRoles(), role -> role.getName()));
    log.info("flat list size={}", data.size());
    log.info("{}", data);
  }


  @Test
  public void testToString() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      users.add(new User(Long.valueOf(i), "n" + i));
    }

    log.info("{}", ListUtil.toString(users, User::getName, ","));
    //
    List<String> strs = new ArrayList<>();
    strs.add("a");
    strs.add("b");
    strs.add("c");
    log.info("simple to string={}", ListUtil.toString(strs));
    log.info("{}", ListUtil.toString(strs, "、"));
  }

  @Test
  public void testSwap() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      users.add(new User(Long.valueOf(i), "n" + i));
    }
    ListUtil.swap(users, 0, 3);
    log.info("{}", ListUtil.toString(users, User::getName, ","));
  }

  @Test
  public void testDistinctObj() throws Exception {

    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId(i % 2 == 0 ? 1L : 2L);
      user.setName("abc" + i);
      users.add(user);
    }

    log.info("{}", ListUtil.distinctObj(users, User::getId));

    List<User> users2 = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId(i % 2 == 0 ? 1L : 3L);
      user.setName("abc" + i);
      user.setAge(10);
      users2.add(user);
    }
    log.info("{}", ListUtil.distinctObj(users2, User::getId));
  }

  @Test
  public void testPage1() throws Exception {
    List<Integer> data = new ArrayList<>();
    for (int i = 0; i < 23; i++) {
      data.add(i);
    }
    log.info("{}", ListUtil.page(data, 10));
  }

  @Test
  public void testPage() throws Exception {
    List<Integer> data = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      data.add(i);
    }
    int pageNum = 1, pageSize = 10;

    while (true) {
      List<Integer> page = ListUtil.page(data, pageNum, pageSize);
      if (ListUtil.isEmpty(page) || page.size() < pageSize) {
        break;
      }
      log.info("page={}", page);
      pageNum++;
    }

  }

  @Test
  public void testPrint() throws Exception {
    List<String> codes = new ArrayList<>();
    codes.add("a");
    codes.add("b");
    log.info("codes={}", codes);
  }

  @Test
  public void testIsAllEmpty() throws Exception {
    List<String> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();

    log.info("{}", ListUtil.isAllEmpty(list1, list2));
  }

  @Test
  public void testNoResize() throws Exception {
    List<String> list = new ArrayList<>(4);

    list.add("1");
    list.add("2");
    list.add("3");
    list.add("4"); // no resize(no grow)

  }

  @Test
  public void testIterator() throws Exception {
    List<String> list = new ArrayList<>(4);

    list.add("1");
    list.add("2");
    list.add("3");
    list.add("4"); // no resize(no grow)

    Iterator<String> it = list.iterator();
    while (it.hasNext()) {
      String item = it.next();
      if (EqualUtil.isEq("2", item)) {
        it.remove();
      }
    }

    log.info("list={}", list);
  }


  @Test
  public void testRemoveNull() throws Exception {
    List<String> list = new ArrayList<>();
    list.add("1");
    list.add(null);
    list.add("3");
    list.add(null);

    log.info("list={}", list);
    ListUtil.removeNull(list);
    log.info("list={}, after remove null", list);
  }

  @Test
  public void testSum() throws Exception {
    int sum = 0;
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setAge(i);
      users.add(user);

      sum += i;
    }

    log.info("list sum={}, our sum={}", ListUtil.sum(users, User::getAge), sum);
  }

  @Test
  public void testSum2() throws Exception {
    List<User> users = new ArrayList<>();
    log.info("sum={}", ListUtil.sum(users, User::getAge));
  }

  @Test
  public void testSum3() throws Exception {
    List<Integer> data = new ArrayList<>();
    int sum = data.stream().map(item -> item).mapToInt(item -> item).sum();
    log.info("sum={}", sum);
  }

  @Test
  public void testSumBigDecimal() throws Exception {
    List<BigDecimal> data = new ArrayList<>();
    data.add(BigDecimal.valueOf(100.01));
    data.add(null);
    data.add(BigDecimal.valueOf(20.9));
    data.add(null);
    log.info("sum={}", ListUtil.sumBigDecimal(data, item -> item));

  }

  @Test
  public void testAddAllError() throws Exception {
    List<Integer> all = new ArrayList<>();
    all.add(1);
    List<Integer> part = Collections.EMPTY_LIST;
    all.addAll(part);
    log.info("all={}", all);
  }

  @Test
  public void testAddAllIfNecessary() throws Exception {
    List<Integer> all = new ArrayList<>();
    all.add(1);
    List<Integer> part = Collections.EMPTY_LIST;

    ListUtil.addAllIfNecessary(all, part);
    log.info("all={}", all);
  }

  @Data
  @AllArgsConstructor
  public static class Tt {

    private int id;
    private int line;
  }

  @Test
  public void testSortAsc() throws Exception {
    List<Tt> list = new ArrayList<>();

    list.add(new Tt(1, 2));
    list.add(new Tt(2, 2));
    list.add(new Tt(5, 2));
    list.add(new Tt(5, 1));
    list.add(new Tt(4, 2));
    list.add(new Tt(7, 2));
    list.add(new Tt(3, 2));

    List<Tt> sortedList = ListUtil.sortAsc(list, Comparator.comparing(Tt::getId), Comparator.comparing(Tt::getLine));
//      list.stream() //
//      .sorted(Comparator.comparing(Tt::getId)) //
//      .sorted(Comparator.comparing(Tt::getLine)) //
//      .collect(Collectors.toList());

    // 原生坑：按line再按id排序
    sortedList.forEach(item -> {
      log.info("id={},line={}", item.getId(), item.getLine());
    });


  }


  @Test
  public void testSortIntegerAsc() throws Exception {
    List<Integer> data = ListUtil.newArrayList(10, 2, 20, 7, 3);
    ListUtil.sortIntegerAsc(data);
    log.info("{}", data);
  }

  @Test
  public void testSortIntegerDesc() throws Exception {
    List<Integer> data = ListUtil.newArrayList(10, 2, 20, 7, 3);
    ListUtil.sortIntegerDesc(data);
    log.info("{}", data);
  }

  @Test
  public void testIntersection() throws Exception {
    List<String> list1 = ListUtil.newArrayList("a", "b", "c");
    List<String> list2 = ListUtil.newArrayList("a", "b", "b", "c");

    log.info("{}", ListUtil.intersection(list1, list2));
    log.info("{}", ListUtil.intersection(list2, list1));

  }

  @Test
  public void testSubBeforeTest() throws Exception {
    List<Tt> list = new ArrayList<>();

    list.add(new Tt(1, 2));
    list.add(new Tt(2, 2));
    list.add(new Tt(5, 2));
    list.add(new Tt(5, 1));
    list.add(new Tt(4, 2));
    list.add(new Tt(6, 2));

    List<Tt> data1 = ListUtil.subBeforeList(list, item -> EqualUtil.isEq(item.getId(), 5));
    log.info("data1={}", data1);

    List<Tt> data2 = ListUtil.subAfterList(list, item -> EqualUtil.isEq(item.getId(), 5));
    log.info("data2={}", data2);
  }


}
