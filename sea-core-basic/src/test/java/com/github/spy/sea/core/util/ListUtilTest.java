package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.domain.Role;
import com.github.spy.sea.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;

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
    public void run17() throws Exception {
        String[] array = new String[]{"1", "s"};

        log.info("{}", ListUtil.toList(array));
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
    public void run26() throws Exception {

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
    public void toMapTest() throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("n" + i);
            users.add(user);
        }

        Map<Long, User> userMap = ListUtil.toMap(users, user -> user.getId());
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
        List<String> strs = new ArrayList<>();
        strs.add("a");
        strs.add("b");
        strs.add("c");
        log.info("simple to string={}", ListUtil.toString(strs));
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
}
