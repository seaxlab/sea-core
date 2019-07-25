package com.github.spy.sea.core.web.util;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.util.IOUtil;
import com.github.spy.sea.core.util.RandomUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-06-25
 * @since 1.0
 */
@Slf4j
public class RequestUtil {

    /**
     * 生产请求日志id
     *
     * @param request
     * @return
     */
    public static String getRequestLogId(HttpServletRequest request) {

        String requestId = request.getHeader(CoreConst.REQUEST_ID);


        if (StringUtil.isNotEmpty(requestId)) {
            return requestId;
        }

        requestId = request.getParameter(CoreConst.REQUEST_ID);

        if (StringUtil.isNotEmpty(requestId)) {
            return requestId;
        }

        return RandomUtil.shortUUID();
    }

    /**
     * 获取requestBody
     *
     * @param request
     * @return
     */
    public static String getRequestBody(HttpServletRequest request) {
        try {
            BufferedReader bufferedReader = request.getReader();
            return IOUtil.read(bufferedReader);
        } catch (Exception e) {
            log.error("获取requestBody失败", e);
        }

        return null;
    }


}
