package com.github.spy.sea.core.lock;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.component.lock.impl.FileLock;
import com.github.spy.sea.core.util.FileUtil;
import com.github.spy.sea.core.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.locks.Lock;


/**
 * module name
 *
 * @author spy
 * @version 1.0 11/18/20
 * @since 1.0
 */
@Slf4j
public class FileLockTest extends BaseCoreTest {

    @Test
    public void lockTest() throws Exception {
        Lock lock = new FileLock("abc");

        lock.lock();
        log.info("----1");
        lock.unlock();
    }

    @Test
    public void lock2Test() throws Exception {
        Lock lock = new FileLock("abc");

        lock.lock();
        log.info("----2");
        lock.unlock();
    }

    @Test
    public void run45() throws Exception {
        Lock lock = new FileLock("abc");


        for (int i = 0; i < 10; i++) {
            lock.lock();
            log.info("----2");
            lock.unlock();
        }
    }

    @Test
    public void trylock1Test() throws Exception {
        Runnable runnable = () -> {
            Lock lock = new FileLock("abc");

            boolean hasLockFlag = lock.tryLock();
            log.info("has lock flag={}", hasLockFlag);
            if (hasLockFlag) {
                log.info("----1");
                lock.unlock();
            }
        };
        runInMultiThread(runnable);
    }


    @Test
    public void trylock2Test() throws Exception {
        Lock lock = new FileLock("abc");

        boolean hasLockFlag = lock.tryLock();
        if (hasLockFlag) {
            log.info("----2");
            lock.unlock();
        }
    }


    @Test
    public void run51() throws Exception {
        String path = PathUtil.join(PathUtil.getUserHome(), "logs", "sea", "file");
        FileUtil.ensureDir(path);

        File file = new File(path + "/a.txt");

        if (!file.exists()) {
            log.info("{}", file.createNewFile());
        }

    }

    @Test
    public void run52() throws Exception {
        String path = PathUtil.join(PathUtil.getUserHome(), "logs", "sea", "file");
        FileUtil.ensureDir(path);

        File file = new File(path + "/a.txt");
        if (!file.exists()) {
            log.info("{}", file.createNewFile());
        }

    }


}
