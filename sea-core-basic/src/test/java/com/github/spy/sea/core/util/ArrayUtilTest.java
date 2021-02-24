package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
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

    List<String> list = null;

    @Before
    public void before() throws Exception {
        super.before();
        list = new ArrayList<>();
        list.add("java");
        list.add("c#");
    }

    @Test
    public void toArrayJdkTest() throws Exception {
        String[] array = {};

        list.toArray(array);
        Assert.assertEquals(0, array.length);
    }

    @Test
    public void toArrayStringTest() throws Exception {

        String[] array = ArrayUtil.toArray(list);
        Assert.assertEquals("java", array[0]);
        Assert.assertEquals("c#", array[1]);
    }

    @Test
    public void toArrayTest() throws Exception {
        String[] array = ArrayUtil.toArray(list, String.class);

        Assert.assertEquals("java", array[0]);
        Assert.assertEquals("c#", array[1]);
    }


    @Test
    public void testConcat() throws Exception {
        String[] a = new String[]{"1", "2"};
        String[] b = new String[]{"3"};
        String[] c = new String[]{};
        String[] d = new String[]{"5"};

        String[] all = ArrayUtil.concat(String.class, a, b, c, d);

        Assert.assertEquals("1", all[0]);
        Assert.assertEquals("2", all[1]);
        Assert.assertEquals("3", all[2]);
        Assert.assertEquals("5", all[3]);
    }

    @Test
    public void test73() throws Exception {
        Object[] arrays = (Object[]) Array.newInstance(Object.class, 1024);
        Assert.assertEquals(1024, arrays.length);

        int[] x = {256};
        Object[] a2 = (Object[]) Array.newInstance(Object.class, x);
        log.info("{}", a2.length);
    }
}
