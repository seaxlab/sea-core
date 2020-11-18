package com.github.spy.sea.core.thread;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/18/20
 * @since 1.0
 */
@Slf4j
public class CleanFileThreadTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        String dir = getUserHome() + "/logs/arthas-cache";
        CleanFileThread thread = new CleanFileThread(dir, 0, 10, TimeUnit.SECONDS, 30, TimeUnit.DAYS);
        thread.start();

        sleep(1000);
    }

}
