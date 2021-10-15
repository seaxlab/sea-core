package com.github.spy.sea.core.thread;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.thread.util.CallableUtil;
import com.github.spy.sea.core.thread.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/15
 * @since 1.0
 */
@Slf4j
public class CallableUtilTest extends BaseCoreTest {

    @Test
    public void testCreate() throws Exception {
        ThreadPoolExecutor tpe = ThreadPoolUtil.createTemp("sea-test", 4, 4);

        log.info("-----");
        Callable<String> callable = CallableUtil.create(true, () -> {
            log.info("get data");
            return "";
        });

        tpe.submit(callable);
        sleepMinute(2);

    }
}
