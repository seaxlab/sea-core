package com.github.spy.sea.core.web.util;

import com.github.spy.sea.core.http.common.MediaType;
import com.github.spy.sea.core.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * 重定向到项目工程内地址
     *
     * @param req             request
     * @param resp            response
     * @param innerProjectUrl 工程内url
     * @throws IOException
     */
    public static void redirect(HttpServletRequest req, HttpServletResponse resp, String innerProjectUrl) throws IOException {
        resp.sendRedirect(req.getContextPath() + innerProjectUrl);
    }

    /**
     * 重定向到指定url
     *
     * @param resp 响应
     * @param url  新地址
     * @throws IOException
     */
    public static void redirect(HttpServletResponse resp, String url) throws IOException {
        resp.sendRedirect(url);
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
            response.getWriter().write(JSONUtil.toStr(obj));
        } catch (Exception e) {
            log.error("response error", e);
        }
    }

}
