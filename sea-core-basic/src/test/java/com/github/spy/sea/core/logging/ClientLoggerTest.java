package com.github.spy.sea.core.logging;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/24
 * @since 1.0
 */
@Slf4j
public class ClientLoggerTest {

    @Test
    public void test16() throws Exception {
        InternalLogger logger = ClientLogger.getLog();

        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }
}
