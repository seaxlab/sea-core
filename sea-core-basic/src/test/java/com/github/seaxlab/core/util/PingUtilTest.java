package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
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
public class PingUtilTest extends BaseCoreTest {


    @Test
    public void run23() throws Exception {
        log.info("{}", PingUtil.ping("10.10.11.243"));
    }

    @Test
    public void run16() throws Exception {
        log.info("{}", PingUtil.ping("www.baidu.com", 3, 3000));
    }


}
