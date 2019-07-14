package com.github.spy.sea.core.web.util;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.util.RandomUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

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

}
