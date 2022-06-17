package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
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

        log.info("final value = {}", PropertyPlaceholderHelper.INSTANCE.replace("${a},${b:hao}", p));
    }

    @Test
    public void testReplace() throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("a", "1");
        log.info("final value = {}", PropertyPlaceholderHelper.INSTANCE.replace("${a},${b:hao}", param));
    }
}
