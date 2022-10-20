package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/16
 * @since 1.0
 */
@Slf4j
public class EqualUtilTest extends BaseCoreTest {

    @Test
    public void isInIntegerListTest() throws Exception {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        log.info("{}", EqualUtil.isIn(1, list));
        log.info("{}", EqualUtil.isIn(6, list));
        log.info("{}", EqualUtil.isIn(-1, list));
    }

    @Test
    public void test29() throws Exception {
        List<Long> left = new ArrayList<>();
        left.add(10000000L);
        left.add(1L);

        List<Long> right = new ArrayList<>();
        right.add(10000000L);

        log.info("{}", left.containsAll(right));
    }

    @Test
    public void testCollectionIsIn() throws Exception {
        List<Long> left = new ArrayList<>();
        left.add(10000000L);
        left.add(1L);

        List<Long> right = new ArrayList<>();
        right.add(10000000L);
        Assert.assertEquals(false, EqualUtil.isIn(left, right));
    }

    @Test
    public void testCollectionIsNotIn() throws Exception {
        List<Long> left = new ArrayList<>();
        left.add(10000000L);
        left.add(1L);

        List<Long> right = new ArrayList<>();
        right.add(10000000L);
        log.info("{}", EqualUtil.isAllNotIn(left, right));
    }

    @Test
    public void testCollectionIsNotIn2() throws Exception {
        List<Long> right = new ArrayList<>();
        right.add(1L);
        right.add(2L);
        right.add(3L);

        List<Long> left = new ArrayList<>();
        left.add(10000000L);
        left.add(1L);

        log.info("{}", EqualUtil.isAllNotIn(left, right));

        List<Long> left2 = new ArrayList<>();
        left2.add(1L);
        left2.add(5L);
        log.info("{}", EqualUtil.isAllNotIn(left2, right));

        List<Long> left3 = new ArrayList<>();
        left3.add(5L);
        log.info("{}", EqualUtil.isAllNotIn(left3, right));
    }

    @Test
    public void testIsEqualList() throws Exception {
        List<String> c1 = new ArrayList<>();
        c1.add("a");
        c1.add("b");
        c1.add("c");

        List<String> c2 = new ArrayList<>();
        c2.add("a");
        c2.add("b");
        c2.add("b");

        log.info("{}", EqualUtil.isEq(c1, c2));

        List<String> c3 = new ArrayList<>();
        c3.add("a");
        c3.add("b");
        c3.add("c");

        log.info("{}", EqualUtil.isEq(c1, c3));
    }

    @Test
    public void testIsEqualSet() throws Exception {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        Set<String> set2 = new HashSet<>();
        set2.add("a");
        set2.add("b");
        log.info("{}", set1.containsAll(set2));
        log.info("{}", SetUtils.difference(set1, set2).toSet());
        log.info("{}", SetUtils.difference(set2, set1).toSet());
    }

    @Test
    public void testIsEqualBigDecimal() throws Exception {
        BigDecimal value1 = new BigDecimal("1.2");
        BigDecimal value2 = new BigDecimal("1.20");
        log.info("{}", EqualUtil.isEq(value1, value2));
        log.info("{}", EqualUtil.isNotEq(value1, value2));
    }
}
