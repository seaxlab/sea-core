package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * disk util
 *
 * @author spy
 * @version 1.0 2019-08-13
 * @since 1.0
 */
@Slf4j
public final class DiskUtil {


  /**
   * get total space of file path
   *
   * @param path
   * @return
   */
  public static long getTotalSpace(String path) {

    File file = new File(path);

    if (file.exists()) {
      return file.getTotalSpace();
    }
    log.warn("file[{}] not exist", path);
    return -1;
  }


  /**
   * get useable space of file path
   *
   * @param path
   * @return
   */
  public static long getUsableSpace(String path) {
    File file = new File(path);

    if (file.exists()) {
      return file.getUsableSpace();
    }
    log.warn("file[{}] not exist", path);

    return -1;
  }


  /**
   * get free space of file path
   *
   * @param path
   * @return
   */
  public static long getFreeSpace(String path) {
    File file = new File(path);

    if (file.exists()) {
      return file.getFreeSpace();
    }
    log.warn("file[{}] not exist", path);

    return -1;
  }

  /**
   * get space used percent
   *
   * @param path
   * @return
   */
  public static double getSpaceUsedPercent(final String path) {
    if (null == path || path.isEmpty()) {
      return -1;
    }

    try {
      File file = new File(path);

      if (!file.exists()) {
        return -1;
      }

      long totalSpace = file.getTotalSpace();

      if (totalSpace > 0) {
        long freeSpace = file.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;

        return usedSpace / (double) totalSpace;
      }
    } catch (Exception e) {
      return -1;
    }

    return -1;
  }

}
