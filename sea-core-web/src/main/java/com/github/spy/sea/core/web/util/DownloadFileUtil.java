package com.github.spy.sea.core.web.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * download file util
 *
 * @author spy
 * @version 1.0 2020/9/17
 * @since 1.0
 */
@Slf4j
public class DownloadFileUtil {

    /**
     * 获取下载文件名称.
     *
     * @param request
     * @param realFileName 真实文件名称
     * @return
     * @throws Exception
     */
    public static String getFilenameStr(HttpServletRequest request,
                                        String realFileName) throws Exception {
        String browName = null;
        String clientInfo = request.getHeader("User-agent");
        log.info("uat={}", clientInfo);

        if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {//
            // IE采用URLEncoder方式处理
            if (clientInfo.indexOf("MSIE 6") > 0
                    || clientInfo.indexOf("MSIE 5") > 0) {// IE6，用GBK，此处实现有局限性
                browName = new String(realFileName.getBytes("UTF-8"),
                        "ISO-8859-1");
            } else {// ie7+用URLEncoder方式
                browName = java.net.URLEncoder.encode(realFileName, "UTF-8");
            }
        } else {// 其他浏览器
            browName = new String(realFileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        return browName;
    }
}
