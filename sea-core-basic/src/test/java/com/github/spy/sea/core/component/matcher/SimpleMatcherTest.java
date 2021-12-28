package com.github.spy.sea.core.component.matcher;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.component.matcher.impl.DefaultSimpleMatcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/28
 * @since 1.0
 */
@Slf4j
public class SimpleMatcherTest extends BaseCoreTest {
    @Test
    public void test16() throws Exception {
        SimpleMatcher matcher = new DefaultSimpleMatcher();

        Assert.assertFalse(matcher.match("aa", "a"));
        Assert.assertTrue(matcher.match("aa", "aa"));
        Assert.assertFalse(matcher.match("aaa", "aa"));
        Assert.assertTrue(matcher.match("aa", "*"));
        Assert.assertTrue(matcher.match("aa", "a*"));
        Assert.assertTrue(matcher.match("ab", "?*"));
        Assert.assertFalse(matcher.match("aab", "c*a*b"));
    }

}
