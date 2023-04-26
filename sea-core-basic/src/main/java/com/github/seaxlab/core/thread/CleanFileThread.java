package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.component.lock.impl.FileLock;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.thread.config.CleanFileConfig;
import com.github.seaxlab.core.util.ListUtil;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.util.TimeUnitUtil;
import com.google.common.base.Preconditions;
import java.io.File;
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
import lombok.extern.slf4j.Slf4j;

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

  private CleanFileConfig config;
  //inner field
  private Lock fileLock;

  private CleanRunnable cleanRunnable;


  private CleanFileThread() {
  }

  public CleanFileThread(String dir, int period, TimeUnit timeUnit, int maxLifeTime, TimeUnit maxLifeTimeUnit) {
    this(Arrays.asList(dir), period, timeUnit, maxLifeTime, maxLifeTimeUnit);
  }

  public CleanFileThread(List<String> dirs, int period, TimeUnit periodTimeUnit, int maxLifeTime,
    TimeUnit maxLifeTimeUnit) {
    Preconditions.checkNotNull(dirs, "file dir cannot be null");
    CleanFileConfig config = new CleanFileConfig();
    config.setDirs(dirs);
    config.setPeriod(period);
    config.setPeriodTimeUnit(periodTimeUnit);
    config.setMaxLifeTime(maxLifeTime);
    config.setMaxLifeTimeUnit(maxLifeTimeUnit);
    this.config = config;
  }

  public CleanFileThread(CleanFileConfig config) {
    this.config = config;
  }


  /**
   * 启动函数
   */
  public void start() {
    Precondition.checkNotNull(config);
    config.check();

    // create file lock
    fileLock = new FileLock(StringUtil.defaultIfBlank(config.getLockNamePrefix(), config.getThreadNamePrefix(), "sea"));

    String threadName = MessageUtil.format("{}-clean-file-thread-{}",
      StringUtil.defaultIfEmpty(config.getThreadNamePrefix(), "sea"), count.incrementAndGet());
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
      if (config.getDelay() > 0) {
        try {
          Thread.sleep(TimeUnitUtil.toMills(config.getDelay(), config.getDelayTimeUnit()));
        } catch (Exception e) {
          log.error("fail to sleep for delay");
        }
      }
      //
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
        Thread.sleep(TimeUnitUtil.toMills(config.getPeriod(), config.getPeriodTimeUnit()));
      } catch (Exception e) {
        log.error("fail to sleep for after clean");
      }
    }

    @Override
    public void destroy() {
      // no op
    }
  }

  private void cleanFile() {
    log.info("clean file thread.");
    List<File> files = this.config.getDirs().stream() //
      .map(File::new)//
      .filter(file -> file.exists() && file.isDirectory()) //
      .flatMap(file -> Arrays.stream(file.listFiles(this.config.getFilenameFilter()))) //
      .filter(file -> file.exists() && file.isFile()) //
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

      long maxMs = TimeUnit.MILLISECONDS.convert(config.getMaxLifeTime(), config.getMaxLifeTimeUnit());

      if ((nowMs - createMs) > maxMs) {
        boolean delFlag = false;
        try {
          delFlag = Files.deleteIfExists(itemFile.toPath());
        } catch (Exception e) {
          log.error("fail to delete file", e);
        } finally {
          log.info("delete file={},success={}", itemFile.getAbsolutePath(), delFlag);
        }
      }
    });
  }


}
