package com.github.spy.sea.core.util;

import cn.hutool.core.lang.Assert;
import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/15
 * @since 1.0
 */
@Slf4j
public class AntPathMatcherTest extends BaseCoreTest {

    @Test
    public void test16() throws Exception {
        PathMatcher pathMatcher = new AntPathMatcher();

        Assert.isTrue(pathMatcher.match("/api/**/ab", "/api/1/ab"));
    }
}
