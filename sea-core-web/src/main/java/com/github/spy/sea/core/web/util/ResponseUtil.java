package com.github.spy.sea.core.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.spy.sea.core.http.common.MediaType;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

/**
 * response util
 *
 * @author spy
 * @version 1.0 2019-07-24
 * @since 1.0
 */
@Slf4j
public class ResponseUtil {

    private ResponseUtil() {
    }

    /**
     * 输出文本格式
     *
     * @param response
     * @param obj
     */
    public static void toText(HttpServletResponse response, Object obj) {
        try {
            response.setContentType(MediaType.TEXT_HTML_VALUE);
            response.getWriter().write(obj == null ? "" : obj.toString());
        } catch (Exception e) {
            log.error("response error", e);
        }
    }

    /**
     * 输出JSON数据
     *
     * @param response
     * @param obj
     */
    public static void toJSON(HttpServletResponse response, Object obj) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect));
        } catch (Exception e) {
            log.error("response error", e);
        }
    }

}
