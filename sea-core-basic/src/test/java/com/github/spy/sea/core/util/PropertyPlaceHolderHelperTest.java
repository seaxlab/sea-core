package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/13
 * @since 1.0
 */
@Slf4j
public class PropertyPlaceHolderHelperTest extends BaseCoreTest {

    @Test
    public void run16() throws Exception {
        Properties p = new Properties();
        p.setProperty("a", "1");

        log.info("final value = {}", PropertyPlaceholderHelper.INSTANCE.replacePlaceholders("${a},${b:hao}", p));
    }
}
