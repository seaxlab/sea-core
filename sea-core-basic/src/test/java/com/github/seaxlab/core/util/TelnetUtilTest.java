package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/27
 * @since 1.0
 */
@Slf4j
public class TelnetUtilTest {
    //TODO

    @Test
    public void run17() throws Exception {
        TelnetUtil telnetUtil = new TelnetUtil("www.baidu.com", 80);

        log.info("{}", telnetUtil.connectTest());
    }
}
