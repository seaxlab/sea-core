package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/1/20
 * @since 1.0
 */
@Slf4j
public class RandomUtilTest extends BaseCoreTest {

    @Test
    public void testNonceStr() throws Exception {
        log.info("{}", RandomUtil.nonceStr());
        log.info("{}", RandomUtil.nonceStr(4));
    }

    @Test
    public void testOneOf() throws Exception {

        for (int i = 0; i < 20; i++) {
            log.info("random={}", RandomUtil.oneOf("abc", "bc", "cc"));
        }
    }

    @Test
    public void testNextInt() throws Exception {
        for (int i = 0; i < 10; i++) {
            log.info("{}", RandomUtil.nextInt());
        }

        for (int i = 0; i < 10; i++) {
            log.info("{}", RandomUtil.nextInt(100, 999));
        }
    }

    @Test
    public void testNextLong() throws Exception {
        for (int i = 0; i < 10; i++) {
            log.info("{}", RandomUtil.nextLong());
        }

        for (int i = 0; i < 10; i++) {
            log.info("{}", RandomUtil.nextLong(100, 999));
        }
    }

    @Test
    public void testNextFloat() throws Exception {
        for (int i = 0; i < 10; i++) {
            log.info("{}", RandomUtil.nextFloat());
        }

        for (int i = 0; i < 10; i++) {
            log.info("{}", RandomUtil.nextFloat(100.01f, 999.99f));
        }
    }

    @Test
    public void testNextDouble() throws Exception {
        for (int i = 0; i < 10; i++) {
            log.info("{}", RandomUtil.nextDouble());
        }

        for (int i = 0; i < 10; i++) {
            log.info("{}", RandomUtil.nextDouble(100.000d, 999.999999d));
        }
    }
}
