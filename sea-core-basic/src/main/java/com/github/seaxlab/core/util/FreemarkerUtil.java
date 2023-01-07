package com.github.seaxlab.core.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */
@Slf4j
public final class FreemarkerUtil {

  private FreemarkerUtil() {
  }

  private static Configuration cfg;

  public static void initConfig(ClassLoader classLoader, String basePackagePath) {
    log.info("try to init freemarker config.");
    //1. 创建Configuration实例
    //Configuration实例是存储 FreeMarker 应用级设置的核心部分
    cfg = new Configuration(Configuration.VERSION_2_3_31);
    //指定模板文件所在路径
//        cfg.setDirectoryForTemplateLoading(new File("D:/templates"));
    cfg.setClassLoaderForTemplateLoading(classLoader, basePackagePath);
    //设置模板文件字符集
    cfg.setDefaultEncoding("UTF-8");
    cfg.setLogTemplateExceptions(false);
    cfg.setWrapUncheckedExceptions(true);
  }

  /**
   * render content
   *
   * @param templateName
   * @param params
   * @return
   */
  public static String render(String templateName, Map<String, Object> params) {
    String result = "";
    if (cfg == null) {
      log.warn("freemarker config is null");
      return result;
    }

    try {
      Template template = cfg.getTemplate(templateName);
      try (Writer out = new StringWriter()) {
        template.process(params, out);
        result = out.toString();
      }
    } catch (Exception e) {
      result = "";
      log.warn("fail to render freemarker template.", e);
    }
    return result;
  }

  /**
   * format
   *
   * @param templateStr
   * @param params
   * @return
   */
  public static String replace(String templateStr, Map<String, Object> params) {
    String result = "";

    try {
      //指定Configuration版本，基于依赖的版本
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
      //模板文件编码
      cfg.setDefaultEncoding("utf-8");
      //模板解析出错，则重新抛出异常
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      //FreeMarker不记录异常，留给应用处理
      cfg.setLogTemplateExceptions(false);
      //将运行时异常包装成FreeMarker异常抛出
      cfg.setWrapUncheckedExceptions(true);
      //
      Template template = new Template("name", new StringReader(templateStr), cfg);
      try (Writer out = new StringWriter()) {
        template.process(params, out);
        result = out.toString();
      }
    } catch (Exception e) {
      log.warn("convert template error", e);
      result = "";
    }
    return result;
  }

}
