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

    /**
     * format
     *
     * @param templateStr
     * @param params
     * @return
     */
    public static String replace(String templateStr, Map<String, Object> params) {
        //指定Configuration版本，基于依赖的版本
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

        //模板文件编码
        cfg.setDefaultEncoding("utf-8");

        //模板解析出错，则重新抛出异常
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        //FreeMarker不记录异常，留给应用处理
        cfg.setLogTemplateExceptions(false);

        //将运行时异常包装成FreeMarker异常抛出
        cfg.setWrapUncheckedExceptions(true);

        String result = "";

        try {
            Template template = new Template("name", new StringReader(templateStr), cfg);

            Writer out = new StringWriter();
            template.process(params, out);

            result = out.toString();
        } catch (Exception e) {
            log.error("convert template error", e);
        }
        return result;
    }

}
