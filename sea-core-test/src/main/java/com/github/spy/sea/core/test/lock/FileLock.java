package com.github.spy.sea.core.test.lock;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 文件锁适用于多jvm
 *
 * @author spy
 * @version 1.0 11/18/20
 * @since 1.0
 */
@Slf4j
public class FileLock implements Lock {

    private String prefix;

    private File file;

    private FileChannel fileChannel;

    // https://docs.oracle.com/javase/8/docs/api/java/nio/channels/FileLock.html
    //File locks are held on behalf of the entire Java virtual machine. They are not suitable for controlling access to a file by multiple threads within the same virtual machine.
    //File-lock objects are safe for use by multiple concurrent threads.
    private java.nio.channels.FileLock innerFileLock;


    private FileLock() {
    }

    public FileLock(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            throw new RuntimeException("FileLock [prefix] cannot be null");
        }
        this.prefix = prefix;
        init();
    }

    private void init() {
        String userHome = System.getProperty("user.home");
        String path = userHome + "/sea/lock";
        File lockDir = new File(path);
        if (!lockDir.exists()) {
            lockDir.mkdirs();
        }

        file = new File(path + "/" + prefix + ".lock");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            log.error("io exception.", e);
        }
    }

    @Override
    public void lock() {
        try {
            fileChannel = new RandomAccessFile(file, "rw").getChannel();
            innerFileLock = fileChannel.lock();
        } catch (OverlappingFileLockException e) {
            log.warn("has OverlappingFileLockException, so loop");
            try {
                boolean hasException = false;
                do {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
                    try {
                        fileChannel.lock();
                    } catch (OverlappingFileLockException exx) {
                        hasException = true;
                    }
                } while (hasException);
            } catch (Exception ex) {
                log.warn("has other exception.");
            }

        } catch (IOException e) {
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        boolean hasLock;
        try {
            innerFileLock = fileChannel.tryLock();
            hasLock = innerFileLock != null;
        } catch (Exception e) {
            hasLock = false;
        }
        return hasLock;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        log.error("unsupported operation.");
        return false;
    }

    @Override
    public void unlock() {
        try {
            if (innerFileLock != null) {
                innerFileLock.release();
            }
            if (fileChannel != null) {
                fileChannel.close();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public Condition newCondition() {
        log.error("unsupported operation.");
        return null;
    }
}
