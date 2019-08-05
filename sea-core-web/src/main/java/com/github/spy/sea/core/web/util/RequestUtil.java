package com.github.spy.sea.core.web.util;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.util.IOUtil;
import com.github.spy.sea.core.util.RandomUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * request util
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

    /**
     * 获取域名(注意：结束符号不是/)
     * http://localhost:8080/user-service/api/user/add -> http://localhost:8080
     *
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();

        int port = request.getServerPort();
        String serverPort = "";

        if (CoreConst.SCHEME_HTTP.equals(request.getScheme())) {
            serverPort = (port == 80) ? "" : ":" + request.getServerPort();
        } else if (CoreConst.SCHEME_HTTPS.equals(request.getScheme())) {
            serverPort = (port == 443) ? "" : ":" + request.getServerPort();
        }

        return scheme + serverName + serverPort;
    }

//    web application 名称为news,你在浏览器中输入请求路径：
//    http://localhost:8080/user-service/api/user/add
//    则执行下面向行代码后打印出如下结果：
//    request.getContextPath()  --> /user-service
//    request.getServletPath()  --> /api/user/add
//    request.getRequestURI()   --> /user-service/api/user/add
//    request.getRealPath("/")  --> /Users/xx/tomcat/webapps/user-service


    /**
     * 获取项目根路径(注意：结束符号不是/)
     * http://localhost:8080/user-service/api/user/add -> http://localhost:8080/user-service
     *
     * @param request
     * @return
     */
    public static String getRootPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return getDomain(request) + contextPath;
    }


}
