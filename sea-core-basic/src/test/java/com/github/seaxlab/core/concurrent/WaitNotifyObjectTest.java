
package com.github.seaxlab.core.concurrent;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class WaitNotifyObjectTest extends BaseCoreTest {

    @Test
    public void removeFromWaitingThreadTable() throws Exception {
        final WaitNotifyObject waitNotifyObject = new WaitNotifyObject();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    log.info("---1");
                    waitNotifyObject.allWaitForRunning(1000);
                    log.info("---2");
                    waitNotifyObject.removeFromWaitingThreadTable();
                }
            });
            log.info("thread start, then join");
            t.start();
            t.join();
            log.info("thread run end.");
            // 顺序执行，.join等待thread执行结束
        }
        Assert.assertEquals(0, waitNotifyObject.waitingThreadTable.size());
    }

}
