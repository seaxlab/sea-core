package com.github.spy.sea.core;

import com.github.spy.sea.core.config.ConfigurationFactory;
import com.github.spy.sea.core.test.AbstractCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-06-16
 * @since 1.0
 */
@Slf4j
public abstract class BaseCoreTest extends AbstractCoreTest {

    // 必须要有 User-Agent
    protected String IP_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=115.204.165.147";

    protected String USER_HOME;

    @Before
    public void before() {
        USER_HOME = getUserHome();
    }


    protected String getUserHome() {
        return ConfigurationFactory.getInstance().getString("user.home");
    }

}
