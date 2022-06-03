package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.Unsafe;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0 2022-06-03
 * @since 1.0
 */
@Slf4j
public class UnsafeUtilTest extends BaseCoreTest {

    @Test
    public void run16() throws Exception {
        Unsafe unsafe = UnsafeUtil.getUnsafe();

        log.debug("{}", unsafe.addressSize());
    }
}
