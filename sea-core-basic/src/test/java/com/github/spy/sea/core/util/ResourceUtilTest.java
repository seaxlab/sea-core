package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/4
 * @since 1.0
 */
@Slf4j
public class ResourceUtilTest extends BaseCoreTest {

    @Test
    public void test18() throws Exception {
        File file = ResourceUtil.getResourceAsFile("classpath:app.properties");
        log.info("file exist={}", file.exists());
    }

    @Test
    public void test26() throws Exception {
        String content = ResourceUtil.getResourceAsString("app.properties");
        log.info("content={}", content);
    }

}
