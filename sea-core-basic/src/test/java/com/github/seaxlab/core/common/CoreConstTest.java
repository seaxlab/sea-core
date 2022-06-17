package com.github.seaxlab.core.common;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/12
 * @since 1.0
 */
@Slf4j
public class CoreConstTest extends BaseCoreTest {

    @Test
    public void testCoreConst() throws Exception {

        log.info("{}", CoreConst.SYS_OPERATOR);
        log.info("{}", CoreConst.SYS_SESSION_CONSOLE);
        log.info("{}", CoreConst.SYS_SESSION_APP);

    }
}
