package com.github.spy.sea.core;

import com.github.spy.sea.core.config.ConfigurationFactory;
import com.github.spy.sea.core.test.AbstractCoreTest;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-06-16
 * @since 1.0
 */
public abstract class BaseCoreTest extends AbstractCoreTest {

    private Logger logger = LoggerFactory.getLogger(BaseCoreTest.class);
    // 必须要有 User-Agent
    protected String IP_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=115.204.165.147";

    protected String USER_HOME;

    @Before
    public void before() throws Exception {
        USER_HOME = getUserHome();
    }


    protected String getUserHome() {
        return ConfigurationFactory.getInstance().getString("user.home");
    }

    protected void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (Exception e) {
        }
    }

    protected void println(Object obj) {
        logger.info("{}", obj);
    }

    /**
     * 获取文件流
     *
     * @param fileInClassPath
     * @return
     */
    protected InputStream getInputStream(String fileInClassPath) {
        assert fileInClassPath != null;
        InputStream inputStream = this.getClass()
                                      .getClassLoader()
                                      .getResourceAsStream(fileInClassPath);
        return inputStream;
    }

}
