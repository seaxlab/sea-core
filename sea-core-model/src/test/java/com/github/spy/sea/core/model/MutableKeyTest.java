package com.github.spy.sea.core.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/27
 * @since 1.0
 */
@Slf4j
public class MutableKeyTest {

    @Test
    public void testMutableKey() throws Exception {
        ImmutableKey key = ImmutableKey.of("1", "2", "3");
        log.info("to string={}", key);

        ImmutableKey key2 = ImmutableKey.parse("1:2:3");
        log.info("to string={}", key2);
    }

    @Test
    public void testEqual() throws Exception {
        ImmutableKey key = ImmutableKey.of("1", "2", "3");
        ImmutableKey key2 = ImmutableKey.of("1", "2", "3");

        log.info("= {}", key == key2);
        log.info("equal {}", key.equals(key2));
    }

}
