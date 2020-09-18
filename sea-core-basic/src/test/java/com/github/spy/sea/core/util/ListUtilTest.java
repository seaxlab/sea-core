package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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


}
