package com.github.seaxlab.core.util;

import com.github.seaxlab.core.exception.BaseAppException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

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
   * get default temp dir.
   *
   * @return
   */
  public static String getDefaultTempDir() {
    return System.getProperty("java.io.tmpdir");
  }

  /**
   * get user home of user who run current process
   *
   * @return
   */
  public static String getUserHome() {
    String userHome = System.getProperty("user.home", "");
    if (StringUtil.isEmpty(userHome)) {
      throw new BaseAppException("user.home is empty");
    }
    return userHome;
  }

  /**
   * get sea home
   * <p>
   * IMPORTANT： plz use this path in most case.
   * </p>
   *
   * @return
   */
  public static String getSeaHome() {
    return join(getUserHome(), "sea");
  }

  /**
   * combine path depends on system
   *
   * @param first
   * @param more
   * @return
   */
  public static String join(String first, String... more) {
    List<String> data = Arrays.stream(more).filter(Objects::nonNull).collect(Collectors.toList());

    String[] e = ArrayUtil.toArray(data, String.class);
    return Paths.get(first, e).toString();
  }

  /**
   * combine path like unix
   *
   * @param first first
   * @param more  more args
   * @return
   */
  public static String joinUnix(String first, String... more) {
    if (more.length == 0) {
      return first;
    }
    StringBuilder path = new StringBuilder();
    path.append(first);

    for (String item : more) {
      if (StringUtil.isEmpty(item)) {
        continue;
      }
      path.append("/").append(item);
    }
    return path.toString();
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
