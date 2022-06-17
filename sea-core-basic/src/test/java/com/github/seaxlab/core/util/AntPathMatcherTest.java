package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
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

    PathMatcher pathMatcher = new AntPathMatcher();

    @Test
    public void testCombine() throws Exception {
        String result = pathMatcher.combine("", null);
        log.info("{}", result);
    }

    @Test
    public void testMatch() throws Exception {
        log.info("{}", pathMatcher.match("/api/**/ab", "/api/1/2/ab"));
        log.info("{}", pathMatcher.match("/api/*/ab", "/api/12/ab"));
        log.info("{}", pathMatcher.match("/api/*/ab", "/api/12/34/ab"));
        log.info("{}", pathMatcher.match("/abc", "/abc"));
    }


}
