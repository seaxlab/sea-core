package com.github.seaxlab.core.concurrent;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.lang.concurrent.AtomicSafeInitializer;
import com.github.seaxlab.core.lang.concurrent.ConcurrentException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/17
 * @since 1.0
 */
@Slf4j
public class AtomicSafeInitializerTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        UserInitializer initializer = new UserInitializer();

        log.info("obj={}", initializer.get());
        log.info("obj={}", initializer.get());

        initializer = new UserInitializer();
        log.info("obj2={}", initializer.get());
        log.info("obj2={}", initializer.get());
    }

    public class UserInitializer extends AtomicSafeInitializer<Object> {

        @Override
        protected Object initialize() throws ConcurrentException {

            return new Object();
        }
    }
}
