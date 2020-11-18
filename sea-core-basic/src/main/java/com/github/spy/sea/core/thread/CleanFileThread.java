package com.github.spy.sea.core.thread;

import com.github.spy.sea.core.message.util.MessageUtil;
import com.github.spy.sea.core.util.ListUtil;
import com.github.spy.sea.core.util.StringUtil;
import com.github.spy.sea.core.util.TimeUnitUtil;
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

    private CleanRunnable cleanRunnable;


    private CleanFileThread() {
    }

    public CleanFileThread(String dir, int delay, TimeUnit delayTimeUnit,
                           int period, TimeUnit timeUnit,
                           int maxLifeTime, TimeUnit maxLifeTimeUnit) {
        this(Arrays.asList(dir), delay, delayTimeUnit, period, timeUnit, maxLifeTime, maxLifeTimeUnit);
    }

    public CleanFileThread(List<String> dirs, int delay, TimeUnit delayTimeUnit,
                           int period, TimeUnit periodTimeUnit,
                           int maxLifeTime, TimeUnit maxLifeTimeUnit) {
        Preconditions.checkNotNull(dirs, "file dir cannot be null");
        this.dirs = dirs;
        this.delay = delay < 0 ? 0 : delay;
        this.delayTimeUnit = delayTimeUnit == null ? TimeUnit.MINUTES : delayTimeUnit;

        this.period = period < 0 ? 1 : period;
        this.periodTimeUnit = periodTimeUnit == null ? TimeUnit.MINUTES : periodTimeUnit;

        this.maxLifeTime = maxLifeTime < 0 ? 1 : maxLifeTime;
        this.maxLifeTimeUnit = maxLifeTimeUnit == null ? TimeUnit.MINUTES : maxLifeTimeUnit;

    }


    public void start() {
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

    public void shutdown() {
        if (cleanRunnable != null) {
            cleanRunnable.stop();
        }
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

    // ----
    private class CleanRunnable extends SuspendedLoopRunnable {

        @Override
        public String name() {
            return "sea-clean-file-runnable";
        }

        @Override
        public void init() {

        }

        @Override
        public void loopRun() {
            if (delay > 0) {
                try {
                    Thread.sleep(TimeUnitUtil.toMills(delay, delayTimeUnit));
                } catch (Exception e) {

                }
            }
            try {
                cleanFile();
            } catch (Exception e) {
                log.error("fail to clean file.", e);
            }
            try {
                Thread.sleep(TimeUnitUtil.toMills(period, periodTimeUnit));
            } catch (Exception e) {

            }
        }

        @Override
        public void destroy() {

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
                    delFlag = itemFile.delete();
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
