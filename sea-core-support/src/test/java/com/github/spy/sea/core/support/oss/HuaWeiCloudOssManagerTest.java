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
public class HuaWeiCloudOssManagerTest extends BaseOssManagerTest {

    @Before
    public void before() {
        ENDPOINT = "https://27.221.114.245:8443";
        ACCESS_KEY = "WZZJCCRGI08LKFVUW6CL";
        SECRET_KEY = "2mo3ntGirIaPbfZVDpqXbJmbKRzRWjwaJgC5qBxb";
        super.before();
    }


}
