package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.domain.User;
import lombok.extern.slf4j.Slf4j;
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
            user.setId(Long.valueOf(i));
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
            user.setId(Long.valueOf(i % 3));
            user.setName("n" + i);
            users.add(user);
        }

        Map<Long, List<User>> userMap = ListUtil.toMapList(users, user -> user.getId());
        log.info("{}", userMap);
    }


    @Test
    public void toStringTest() throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User(Long.valueOf(i), "n" + i));
        }

        log.info("{}", ListUtil.toString(users, User::getName, ","));
    }
}
