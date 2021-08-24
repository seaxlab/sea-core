package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.domain.User;
import com.github.spy.sea.core.model.Diff;
import lombok.extern.slf4j.Slf4j;
import org.javers.common.collections.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/11
 * @since 1.0
 */
@Slf4j
public class SetUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        Set<String> set = new HashSet<>();
        set.add("abc");
        set.add("hello");
        Assert.assertEquals("abc;hello", SetUtil.toString(set, ";"));
    }

    @Test
    public void toStringTest() throws Exception {
        Set<User> set = new HashSet<>();
        set.add(new User(10L, "s10"));
        set.add(new User(2l, "s2"));
        set.add(new User(20L, "s20"));

        Assert.assertEquals("s2,s10,s20", SetUtil.toString(set, User::getName, ","));
    }


    @Test
    public void run30() throws Exception {
        Set<Set<String>> allSet = new HashSet<>();

        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        Set<String> set2 = new HashSet<>();
        set2.add("b");
        set2.add("c");

        Set<String> set3 = new HashSet<>();
        set3.add("c");

        allSet.add(set1);
        allSet.add(set2);
        allSet.add(set3);

        log.info("{}", SetUtil.intersection(allSet));
    }

    @Test
    public void intersectionTest1() throws Exception {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        Set<String> set2 = new HashSet<>();
        set2.add("b");
        set2.add("c");
        log.info("{}", SetUtil.intersection(set1, set2));
    }

    @Test
    public void intersectionTest2() throws Exception {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        Set<String> set2 = new HashSet<>();
        set2.add("b");
        set2.add("c");

        List<Set<String>> list = new ArrayList<>();
        list.add(set1);
//        list.add(set2);
        log.info("{}", SetUtil.intersection(list));
    }

    @Test
    public void differenceTest() throws Exception {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        Set<String> set2 = new HashSet<>();
        set2.add("b");
        set2.add("c");
        log.info("{}", SetUtil.difference(set1, set2));
    }

    @Test
    public void unionTest() throws Exception {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        Set<String> set2 = new HashSet<>();
        set2.add("b");
        set2.add("c");
        set2.add("d");
        log.info("{}", SetUtil.union(set1, set2));
    }

    @Test
    public void unionListTest() throws Exception {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        Set<String> set2 = new HashSet<>();
        set2.add("b");
        set2.add("c");
        set2.add("d");

        Set<String> set3 = new HashSet<>();
        set3.add("b");
        set3.add("c");
        set3.add("da");
        List list = Arrays.asList(set1, set2, set3);

        log.info("{}", SetUtil.union(list));
        log.info("{},{},{}", set1, set2, set3);
    }


    @Test
    public void equalTest() throws Exception {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        Set<String> set2 = new HashSet<>();
        set2.add("b");
        set2.add("c");
        set2.add("d");

        Set<String> set3 = new HashSet<>();
        set3.add("b");
        set3.add("c");
        set3.add("a");

        log.info("{}", SetUtil.isEqual(set1, set2));
        log.info("{}", SetUtil.isEqual(set1, set3));
    }

    @Test
    public void run132() throws Exception {
        Set<User> set1 = new HashSet<>();

        User user = new User();
        user.setId(1L);
        set1.add(user);

        Set<User> set2 = new HashSet<>();
        User user2 = new User();
        user2.setId(1L);
        set2.add(user2);

        log.info("{}", SetUtil.isEqual(set1, set2));
    }

    @Test
    public void run170() throws Exception {
        Set<String> set1 = Sets.asSet("1", "2");
        Set<String> set2 = Sets.asSet("2", "3");
        Set<String> set3 = Sets.asSet("2", "3");
        Set<String> set4 = Sets.asSet("2");
        Set<String> set5 = Sets.asSet("1");

        List<Set<String>> list = new ArrayList<>();
        list.add(set1);
        list.add(set2);
        list.add(set3);
        list.add(set4);
        list.add(set5);

        for (int i = 0; i < 10; i++) {

        }

    }

    @Test
    public void firstTest() throws Exception {

        Set<String> set1 = Sets.asSet("1", "2");
        Set<String> set2 = Sets.asSet("2", "3");
        log.info("{}", SetUtil.first(set1));
    }

    @Test
    public void testToSet() throws Exception {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setId(1L);
        user.setName("1");
        users.add(user);

        user = new User();
        user.setId(2L);
        user.setName("2");
        users.add(user);

        user = new User();
        user.setId(2L);
        user.setName("12");
        users.add(user);

        log.info("{}", SetUtil.toSet(users, User::getId));
        log.info("{}", SetUtil.toSet(users, User::getName));

    }

    @Test
    public void testDiff() throws Exception {
        Set<String> set1 = Sets.asSet("1", "2");
        Set<String> set2 = Sets.asSet("2", "3");

        Diff<String> diff = SetUtil.diff(set1, set2);

        log.info("common={}", diff.getIntersections());
        log.info("lefts={}", diff.getLefts());
        log.info("rights={}", diff.getRights());
    }


}
