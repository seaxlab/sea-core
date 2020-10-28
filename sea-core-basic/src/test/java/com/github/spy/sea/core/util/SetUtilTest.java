package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void run53() throws Exception {
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
    public void run66() throws Exception {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        Set<String> set2 = new HashSet<>();
        set2.add("b");
        set2.add("c");

        List<Set<String>> list = new ArrayList<>();
        list.add(set1);
        list.add(set2);
        log.info("{}", SetUtil.intersection(list));
    }

}
