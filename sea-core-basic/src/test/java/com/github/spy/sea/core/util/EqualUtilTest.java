package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
