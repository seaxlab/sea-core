package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.test.AbstractCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/2/28
 * @since 1.0
 */
@Slf4j
public class ThreadContextTest extends AbstractCoreTest {

    @Test
    public void run17() throws Exception {

        Boolean hit = ThreadContext.get("abc"); //ok
        boolean hit2 = ThreadContext.get("abc"); // throw NPE
    }

    @Test
    public void run25() throws Exception {

    }


}
