package com.github.spy.sea.core.support.oss;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Slf4j
public class AliyunOssManagerTest extends BaseOssManagerTest {

    @Before
    public void before() {
        ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
        ACCESS_KEY = "8PIhaKLfrSBFvK1f";
        SECRET_KEY = "uK1uKmOtX2HP91kpVWRixWEiCh933J";

        super.before();
    }


}
