package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * properties util
 *
 * @author spy
 * @version 1.0 2020/3/24
 * @since 1.0
 */
@Slf4j
public final class PropertiesUtil {

  private PropertiesUtil() {
  }

  /**
   * load from file
   *
   * @param classPath class path file
   * @return properties
   */
  public static Properties load(String classPath) {
    Properties pros = new Properties();
    //
    try (InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(classPath)) {
      if (in == null) {
        log.info("load properties [{}] not exist", classPath);
        return pros;
      }
      pros.load(in);
      return pros;
    } catch (Exception e) {
      log.error("fail to load properties file", e);
    }

    return new Properties();
  }

  /**
   * load input stream.
   *
   * @param inputStream
   * @return
   * @throws IOException
   */
  public static Properties load(InputStream inputStream) throws IOException {
    Properties props = new Properties();
    //
    if (inputStream == null) {
      return props;
    }

    props.load(inputStream);
    return props;
  }


  /**
   * load into Map
   *
   * @param path
   * @return
   */
  public static Map<String, String> loadForMap(String path) {
    Map<String, String> map = new HashMap<>();
    Properties pros = load(path);
    Enumeration<String> en = (Enumeration<String>) pros.propertyNames();
    while (en.hasMoreElements()) {
      String key = en.nextElement();
      map.put(key, pros.getProperty(key));
    }
    return map;
  }

  /**
   * param str to properties.
   *
   * @param paramStr
   * @return
   */
  public static Properties parse(String paramStr) {
    Properties props = new Properties();

    String[] values = paramStr.split("&");

    for (int i = 0; i < values.length; i++) {
      String value = values[i];
      String[] kv = value.split("=");
      if (kv.length == 1) {
        props.setProperty(kv[0], "");
      } else if (kv.length == 2) {
        props.setProperty(kv[0], kv[1]);
      }
    }

    return props;
  }

  /**
   * write properties file.
   *
   * @param properties
   * @param fileName
   * @return
   */
  public static boolean write(Properties properties, String fileName) {
    return write(properties, fileName, null);
  }

  /**
   * write properties
   *
   * @param properties
   * @param fileName
   * @return
   */
  public static boolean write(Properties properties, String fileName, String comment) {
    boolean successFlag = true;

    try (FileOutputStream out = new FileOutputStream(fileName);
         OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      properties.store(writer, comment);
    } catch (IOException e) {
      successFlag = false;
      log.error("io exception", e);
    }

    return successFlag;
  }

}
