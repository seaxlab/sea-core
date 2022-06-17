package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.component.lock.impl.FileLock;
import com.github.seaxlab.core.util.ListUtil;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.util.TimeUnitUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * clean file thread.
 *
 * @author spy
 * @version 1.0 11/18/20
 * @since 1.0
 */
@Slf4j
public class CleanFileThread {

    private static final AtomicLong count = new AtomicLong(0);
    /**
     * 需要清理的目录，只清理一级目录
     */
    private List<String> dirs;
    /**
     * 线程启动延迟时间
     */
    private int delay;
    /**
     * 延迟时间单位
     */
    private TimeUnit delayTimeUnit;
    /**
     * 间隔时间
     */
    private int period;
    /**
     * 间隔时间单位
     */
    private TimeUnit periodTimeUnit;

    /**
     * 多长时间之前的文件被清理掉
     */
    private int maxLifeTime;
    /**
     * 最大文件有效期 时间单位
     */
    private TimeUnit maxLifeTimeUnit;

    /**
     * 文件过滤
     */
    private FilenameFilter filenameFilter;

    /**
     * 线程名称前缀
     */
    private String threadNamePrefix;

    /**
     * 文件锁，一台机器有多个jvm时，针对的是同一个dirs，建议加锁
     */
    private String lockNamePrefix;

    //inner field
    private Lock fileLock;

    private CleanRunnable cleanRunnable;


    private CleanFileThread() {
    }

    public CleanFileThread(String dir,
                           int period, TimeUnit timeUnit,
                           int maxLifeTime, TimeUnit maxLifeTimeUnit) {
        this(Arrays.asList(dir), period, timeUnit, maxLifeTime, maxLifeTimeUnit);
    }

    public CleanFileThread(List<String> dirs,
                           int period, TimeUnit periodTimeUnit,
                           int maxLifeTime, TimeUnit maxLifeTimeUnit) {
        Preconditions.checkNotNull(dirs, "file dir cannot be null");
        this.dirs = dirs;

        this.period = period < 0 ? 1 : period;
        this.periodTimeUnit = periodTimeUnit == null ? TimeUnit.MINUTES : periodTimeUnit;

        this.maxLifeTime = maxLifeTime < 0 ? 1 : maxLifeTime;
        this.maxLifeTimeUnit = maxLifeTimeUnit == null ? TimeUnit.MINUTES : maxLifeTimeUnit;

    }


    /**
     * 启动函数
     */
    public void start() {
        delay = delay <= 0 ? 0 : delay;
        delayTimeUnit = delayTimeUnit == null ? TimeUnit.MINUTES : delayTimeUnit;

        // create file lock
        fileLock = new FileLock(StringUtil.defaultIfBlank(lockNamePrefix, getThreadNamePrefix(), "sea"));

        String threadName = MessageUtil.format("{}-clean-file-thread-{}",
                StringUtil.defaultIfEmpty(getThreadNamePrefix(), "sea")
                , count.incrementAndGet());
        cleanRunnable = new CleanRunnable();
        Thread thread = new Thread(cleanRunnable);
        thread.setName(threadName);
        thread.setDaemon(true);
        thread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> cleanRunnable.stop()));
    }

    /**
     * 停止
     */
    public void shutdown() {
        if (cleanRunnable != null) {
            cleanRunnable.stop();
        }
    }

    // get/set

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public TimeUnit getDelayTimeUnit() {
        return delayTimeUnit;
    }

    public void setDelayTimeUnit(TimeUnit delayTimeUnit) {
        this.delayTimeUnit = delayTimeUnit;
    }

    public FilenameFilter getFilenameFilter() {
        return filenameFilter;
    }

    public void setFilenameFilter(FilenameFilter filenameFilter) {
        this.filenameFilter = filenameFilter;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public String getLockNamePrefix() {
        return lockNamePrefix;
    }

    public void setLockNamePrefix(String lockNamePrefix) {
        this.lockNamePrefix = lockNamePrefix;
    }

    // ----
    private class CleanRunnable extends SuspendedLoopRunnable {

        @Override
        public String name() {
            return "sea-clean-file-runnable";
        }

        @Override
        public void init() {
            // no op
        }

        @Override
        public void loopRun() {
            if (delay > 0) {
                try {
                    Thread.sleep(TimeUnitUtil.toMills(delay, delayTimeUnit));
                } catch (Exception e) {
                    log.error("fail to sleep");
                }
            }
            boolean hasLockFlag = fileLock.tryLock();
            try {
                // file lock
                cleanFile();
            } catch (Exception e) {
                log.error("fail to clean file.", e);
            } finally {
                if (hasLockFlag) {
                    fileLock.unlock();
                }
            }
            try {
                Thread.sleep(TimeUnitUtil.toMills(period, periodTimeUnit));
            } catch (Exception e) {
                log.error("fail to sleep");
            }
        }

        @Override
        public void destroy() {
            // no op
        }
    }

    private void cleanFile() {
        log.info("clean file thread.");
        List<File> files = this.dirs.stream()
                                    .map(dir -> new File(dir))
                                    .filter(file -> file.exists() && file.isDirectory())
                                    .flatMap(file -> Arrays.stream(file.listFiles(this.filenameFilter)))
                                    .filter(file -> file.exists() && file.isFile())
                                    .collect(Collectors.toList());

        if (ListUtil.isEmpty(files)) {
            return;
        }
        long nowMs = System.currentTimeMillis();
        files.forEach(itemFile -> {
            if (itemFile.isDirectory() || (!itemFile.exists())) {
                return;
            }
            Path path = itemFile.toPath();
            BasicFileAttributes fileAttr = null;
            try {
                fileAttr = Files.readAttributes(path, BasicFileAttributes.class);
            } catch (IOException e) {
                log.error("io exception.", e);
            }
            if (fileAttr == null) {
                return;
            }

            FileTime creationTime = fileAttr.creationTime();
            long createMs = creationTime.to(TimeUnit.MILLISECONDS);

            long maxMs = TimeUnit.MILLISECONDS.convert(maxLifeTime, maxLifeTimeUnit);

            if ((nowMs - createMs) > maxMs) {
                boolean delFlag = false;
                try {
                    delFlag = Files.deleteIfExists(itemFile.toPath());
                } catch (Exception e) {
                    delFlag = false;
                    log.error("fail to delete file", e);
                } finally {
                    log.info("delete file={},success={}", itemFile.getAbsolutePath(), delFlag);
                }
            }
        });
    }


}
