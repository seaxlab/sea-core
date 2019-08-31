package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public class FileUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        String txt = FileUtil.readFormClasspath("util/users.json");

        log.info("txt={}", txt);
    }
}
