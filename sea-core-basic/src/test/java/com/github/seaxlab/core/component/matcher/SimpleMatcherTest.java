package com.github.seaxlab.core.component.matcher;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.matcher.impl.DefaultSimpleMatcher;
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


    @Test
    public void testMatch() throws Exception {
        //isMatch("aa", "a") →false
        //isMatch("aa", "aa") →true
        //isMatch("aaa", "aa") →false
        //isMatch("aa", "*") →true
        //isMatch("aa", "a*") →true
        //isMatch("ab", "?*") →true
        //isMatch("aab", "c*a*b") →false
        log.info("{}", isMatch("aa", "a"));
        log.info("{}", isMatch("aa", "aa"));
        log.info("{}", isMatch("aaa", "aa"));
        log.info("{}", isMatch("aa", "*"));
        log.info("{}", isMatch("aa", "a*"));
        log.info("{}", isMatch("ab", "?*"));
        log.info("{}", isMatch("aab", "c*a*b"));
    }


    // leecode
    public boolean isMatch(String str, String wildcard) {
        int i = 0;
        int j = 0;
        int starIndex = -1;
        int iIndex = -1;

        while (i < str.length()) {
            if (j < wildcard.length() && (wildcard.charAt(j) == '?' || wildcard.charAt(j) == str.charAt(i))) {
                ++i;
                ++j;
            } else if (j < wildcard.length() && wildcard.charAt(j) == '*') {
                starIndex = j;
                iIndex = i;
                j++;
            } else if (starIndex != -1) {
                j = starIndex + 1;
                i = iIndex + 1;
                iIndex++;
            } else {
                return false;
            }
        }

        while (j < wildcard.length() && wildcard.charAt(j) == '*') {
            ++j;
        }

        return j == wildcard.length();
    }

}
