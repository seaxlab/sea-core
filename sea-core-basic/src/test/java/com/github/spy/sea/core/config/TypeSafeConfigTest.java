package com.github.spy.sea.core.config;

import com.github.spy.sea.core.BaseCoreTest;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-25
 * @since 1.0
 */
@Slf4j
public class TypeSafeConfigTest extends BaseCoreTest {

    @Before
    public void before() {
        System.setProperty("sea.config.env", "local");
    }


    @Test
    public void run17() throws Exception {
        Config config = ConfigFactory.load();

        config.hasPath("abc");


        // IMPORTANT config is IMMUTABLE
        config = config.withValue("abc", ConfigValueFactory.fromAnyRef("123"));

        String value = config.getString("abc");

        Assert.assertEquals(value, "123");
    }

    @Test
    public void testOverride() throws Exception {
        Config config = ConfigFactory.load();

        log.info("config={}", config);

        config = config.withValue("sea.config.a2", ConfigValueFactory.fromAnyRef("sbc"));
        log.info("config={}", config);

        // 其存储结构是json，因此会覆盖
        config = config.withValue("sea.config", ConfigValueFactory.fromAnyRef("ccc"));
        log.info("config={}", config);

    }
}
