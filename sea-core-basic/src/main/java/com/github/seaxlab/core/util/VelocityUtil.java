package com.github.seaxlab.core.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * velocity util
 *
 * @author spy
 * @version 1.0 2022/11/26
 * @since 1.0
 */
@Slf4j
public final class VelocityUtil {

  private VelocityUtil() {
  }

  private static final Map<String, VelocityEngine> ENGINE_MAP = new ConcurrentHashMap<>();
  private static final int MAX_SIZE = 20;
  //private static final String CLASS_PATH_PRE = "templates/";

  /**
   * render from classpath
   * <ul>
   * <li>org.apache.velocity.runtime.resource.loader.JarResourceLoader</li>
   * <li>org.apache.velocity.runtime.resource.loader.DataSourceResourceLoader</li>
   * <li>org.apache.velocity.runtime.resource.loader.URLResourceLoader</li>
   * <li>org.apache.velocity.runtime.resource.loader.StringResourceLoader</li>
   * </ul>
   *
   * @param templateFullPath such as '/template/velocity/test.vm'
   * @param params
   * @return
   */
  public static String render(String templateFullPath, Map<String, Object> params) {
    VelocityEngine engine = ENGINE_MAP.computeIfAbsent(templateFullPath, key -> {
      VelocityEngine ve = new VelocityEngine();
      Properties properties = new Properties();
      properties.setProperty(VelocityEngine.INPUT_ENCODING, "UTF-8");
      //"class": classpath, "file": file system
      properties.setProperty(VelocityEngine.RESOURCE_LOADERS, "classpath");
      //properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, viewPage);
      properties.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
      ve.init(properties);
      if (ENGINE_MAP.size() > MAX_SIZE) {
        log.warn("engine map reach MAX_SIZE[{}], so clean.", MAX_SIZE);
        ENGINE_MAP.clear();
      }

      return ve;
    });

    VelocityContext context = new VelocityContext();
    if (params != null && !params.isEmpty()) {
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        context.put(entry.getKey(), entry.getValue());
      }
    }

    Template template = engine.getTemplate(templateFullPath);
    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    return writer.toString();
  }


  //-----------------
  private static VelocityEngine getEngine(String name) {
    return ENGINE_MAP.get(name);
  }
}
