package com.github.seaxlab.core.lang.logging;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/19
 * @since 1.0
 */
@Slf4j
public class LogTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        Object[] args = {"1", "2", "3"};

        log.info(" args={}", JSONUtil.toStr(args));
        log.info(" args={}", JSONUtil.toStr(null));
        NullPointerException exception = new NullPointerException();
        log.warn("abc", exception);
    }
}
