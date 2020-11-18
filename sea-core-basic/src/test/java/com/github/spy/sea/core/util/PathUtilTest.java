package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.config.Configuration;
import com.github.spy.sea.core.config.ConfigurationFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-05
 * @since 1.0
 */
@Slf4j
public class PathUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {

        Assert.assertEquals(PathUtil.join("/Users/smith", "//file.txt"), "/Users/smith/file.txt");
        Assert.assertEquals(PathUtil.join("/Users/smith", "//work//", "//file.txt"), "/Users/smith/work/file.txt");

    }

    @Test
    public void run27() throws Exception {
        Configuration cfg = ConfigurationFactory.getInstance();
        String userHome = cfg.getString("user.home");

        String logPath = PathUtil.join(userHome, "logs", "sea", "jstack");
        log.info("logPath={}", logPath);
    }
}
