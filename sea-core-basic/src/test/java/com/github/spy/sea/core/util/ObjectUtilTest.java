package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/10
 * @since 1.0
 */
@Slf4j
public class ObjectUtilTest extends BaseCoreTest {

    @Test
    public void testDefaultIfNull() throws Exception {
        Boolean flag = null;
        log.info("flag={}", ObjectUtil.defaultIfNull(flag, Boolean.TRUE));
    }

    @Test
    public void testProperties2Object() throws Exception {
        User user = new User();
        log.info("user={}", user);

        Properties properties = new Properties();
        properties.setProperty("age", "18");
        properties.setProperty("name", "taotao");

        ObjectUtil.properties2Object(properties, user);
        log.info("after set properties, user={}", user);
    }

}
