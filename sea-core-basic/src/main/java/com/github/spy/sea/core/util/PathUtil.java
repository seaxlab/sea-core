package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.file.Paths;

/**
 * path util
 *
 * @author spy
 * @version 1.0 2019-08-05
 * @since 1.0
 */
@Slf4j
public final class PathUtil {

    /**
     * combine path
     *
     * @param first
     * @param more
     * @return
     */
    public static String join(String first, String... more) {
        return Paths.get(first, more).toString();
    }

    /**
     * get file from classpath
     *
     * @param filePath
     * @return
     */
    public static String getPathFromClassPath(String filePath) {
        URL url = PathUtil.class.getClassLoader().getResource(filePath);

        if (url != null) {
            return url.getPath();
        }

        log.warn("file[{}] is not in classpath", filePath);
        return null;
    }
}
