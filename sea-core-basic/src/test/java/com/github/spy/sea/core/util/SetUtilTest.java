package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
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
}
