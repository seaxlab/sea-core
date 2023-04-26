package com.github.seaxlab.core.thread.config;

import java.io.FilenameFilter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Data;

/**
 * clean file config
 *
 * @author spy
 * @version 1.0 2023/04/26
 * @since 1.0
 */
@Data
public class CleanFileConfig {

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


  public void check() {
    delay = Math.max(delay, 0);
    delayTimeUnit = delayTimeUnit == null ? TimeUnit.MINUTES : delayTimeUnit;
    //
    this.period = period < 0 ? 1 : period;
    this.periodTimeUnit = periodTimeUnit == null ? TimeUnit.MINUTES : periodTimeUnit;

    this.maxLifeTime = maxLifeTime < 0 ? 1 : maxLifeTime;
    this.maxLifeTimeUnit = maxLifeTimeUnit == null ? TimeUnit.MINUTES : maxLifeTimeUnit;
  }

}
