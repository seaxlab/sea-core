package com.github.spy.sea.core.util;

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
     * @param filePath
     * @return
     */
    public static long getTotalSpace(String filePath) {

        File file = new File(filePath);

        if (file.exists()) {
            return file.getTotalSpace();
        }
        log.warn("file[{}] not exist", filePath);
        return 0;
    }


    /**
     * get useable space of file path
     *
     * @param filePath
     * @return
     */
    public static long getUsableSpace(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            return file.getUsableSpace();
        }
        log.warn("file[{}] not exist", filePath);

        return 0;
    }


    /**
     * get free space of file path
     *
     * @param filePath
     * @return
     */
    public static long getFreeSpace(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            return file.getFreeSpace();
        }
        log.warn("file[{}] not exist", filePath);

        return 0;
    }


}
