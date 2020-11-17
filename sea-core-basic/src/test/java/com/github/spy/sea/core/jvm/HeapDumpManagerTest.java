package com.github.spy.sea.core.jvm;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.jvm.manager.HeapDumpManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/17/20
 * @since 1.0
 */
@Slf4j
public class HeapDumpManagerTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        HeapDumpManager heapDumpManager = new HeapDumpManager();
        heapDumpManager.heapDump(true);
    }
}
