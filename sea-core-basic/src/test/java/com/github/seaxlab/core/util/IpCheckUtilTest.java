package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/28
 * @since 1.0
 */
@Slf4j
public class IpCheckUtilTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        log.info("{}", "192.168.0".matches("192.*"));
        log.info("{}", IpCheckUtil.isPermitted("192.168.0.1", "192.*"));

        String ipWhite = "1.168.1.1;" + //设置单个IP的白名单 //
                // "192.*;" //设置ip通配符,对一个ip段进行匹配
                "192.168.3.17-192.168.3.38;" //设置一个IP范围
                + "192.168.4.0/24"; //設置一个网段
        log.info("{}", IpCheckUtil.isPermitted("1.168.1.1", ipWhite)); //true
        log.info("{}", IpCheckUtil.isPermitted("192.168.1.2", ipWhite)); //false
        log.info("{}", IpCheckUtil.isPermitted("192.168.3.16", ipWhite)); //false
        log.info("{}", IpCheckUtil.isPermitted("192.168.3.37", ipWhite)); //true
        log.info("{}", IpCheckUtil.isPermitted("192.168.4.1", ipWhite)); //true
    }
}
