package com.github.seaxlab.core.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/1
 * @since 1.0
 */
@Slf4j
public class EnvTest {

    @Test
    public void run16() throws Exception {
        log.info("is local model={}", Env.isLocalMode());
    }

}
