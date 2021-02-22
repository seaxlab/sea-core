package com.github.spy.sea.core.util;

import com.github.spy.sea.core.config.Configuration;
import com.github.spy.sea.core.config.ConfigurationFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

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

    private PathUtil() {
    }


    /**
     * get user home of user who run current process
     *
     * @return
     */
    public static final String getUserHome() {
        Configuration cfg = ConfigurationFactory.getInstance();
        String userHome = cfg.getString("user.home", "");
        if (StringUtil.isEmpty(userHome)) {
            throw new RuntimeException("user.home is empty");
        }
        return userHome;
    }

    /**
     * get sea home
     * IMPORTANT： plz use this path in most case.
     *
     * @return
     */
    public static final String getSeaHome() {
        return join(getUserHome(), "sea");
    }

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

    /**
     * normalize path
     * <pre>
     *   /foo/../bar          -->   /bar
     *   /foo/../bar/         -->   /bar/
     * </pre>
     *
     * @param filename
     * @return
     */
    public static String normalize(String filename) {
        return FilenameUtils.normalize(filename);
    }

}
