package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/21
 * @since 1.0
 */
@Slf4j
public class ArrayUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("c#");
        String[] arrays = ArrayUtil.toArray(list);
        log.info("arrays={}", arrays);

        String[] a = new String[]{"1", "s"};
        log.info("{}{}{}", a);
    }

    @Test
    public void testConcat() throws Exception {
        String[] a = new String[]{"1", "2"};
        String[] b = new String[]{"3"};
        String[] c = new String[]{};
        String[] d = new String[]{"5"};

        String[] all = ArrayUtil.concat(String.class, a, b, c, d);
        log.info("{},{},{},{},{},{},{},{}", all);

    }
}
