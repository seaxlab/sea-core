package com.github.seaxlab.core.jvm;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.jvm.manager.StackManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/29
 * @since 1.0
 */
@Slf4j
public class StackManagerTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        log.info(StackManager.dumpStandard());
    }

}
