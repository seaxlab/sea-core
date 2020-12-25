package com.github.spy.sea.core.lock.impl;

import com.github.spy.sea.core.util.FileUtil;
import com.github.spy.sea.core.util.PathUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
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
        if (StringUtil.isEmpty(prefix)) {
            throw new RuntimeException("FileLock [prefix] cannot be null");
        }
        this.prefix = prefix;
        init();
    }

    private void init() {
        String path = PathUtil.join(PathUtil.getUserHome(), "logs", "sea", "lock");
        FileUtil.ensureDir(path);

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
            fileChannel = new RandomAccessFile(file, "rw").getChannel();
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
