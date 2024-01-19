package com.github.seaxlab.core.lang.jvm;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.lang.jvm.manager.HeapDumpManager;
import com.github.seaxlab.core.model.Result;
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
    Result<String> result = heapDumpManager.heapDump(true);
    log.info("result={}", JacksonUtil.toString(result));
  }
}
