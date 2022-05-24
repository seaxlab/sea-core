package com.github.spy.sea.core.security;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.security.util.DESUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/24
 * @since 1.0
 */
@Slf4j
public class DESUtilTest extends BaseCoreTest {

    @Test
    public void testAll() throws Exception {
        String key = "12345678";
        String encrypt = DESUtil.encrypt(key, "abcdef");
        String decrypt = DESUtil.decrypt(key, encrypt);

        log.info("encrypt={}", encrypt);
        log.info("decrypt={}", decrypt);
    }
}
