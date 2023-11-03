package com.github.seaxlab.core.component.perf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import lombok.extern.slf4j.Slf4j;

/**
 * common data collector for <font color='red'> system level, this is very important </font>
 *
 * @author spy
 * @version 1.0 2020/4/18
 * @since 1.0
 */
@Slf4j
public class DataStats {

  private static DataStats current = null;
  /**
   * LongAdder比AtomicLong有更好的性能
   */
  private final Map<String, LongAdder> cache = new ConcurrentHashMap<>();

  private DataStats() {
  }

  public static DataStats currentStatsHolder() {
    if (null == current) {
      synchronized (DataStats.class) {
        if (null == current) {
          current = new DataStats();
        }
      }
    }
    return current;
  }

  /**
   * increment
   *
   * @param metric
   */
  public void count(String metric) {
    try {
      cache.putIfAbsent(metric, new LongAdder());
      cache.get(metric).increment();
    } catch (Exception e) {
      // ignore
    }
  }

  /**
   * reset old and new one.
   *
   * @return old data stats holder.
   */
  public static synchronized DataStats getAndReset() {
    DataStats tmp = new DataStats();
    DataStats old = currentStatsHolder();
    current = tmp;
    return old;
  }

  /**
   * get cache.
   *
   * @return
   */
  public Map<String, LongAdder> getCache() {
    return cache;
  }
}
