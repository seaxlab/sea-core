package com.github.spy.sea.core.web.util;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.http.common.HttpHeaderConst;
import com.github.spy.sea.core.util.IOUtil;
import com.github.spy.sea.core.util.RandomUtil;
import com.github.spy.sea.core.util.StringUtil;
import com.github.spy.sea.core.web.model.RequestDTO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
     * print simple request info
     *
     * @param request
     */
    public static void logSimple(HttpServletRequest request) {
        log.info("request uri={},queryString={},userAgent={}", request.getRequestURI(), request.getQueryString(), getUserAgent(request));
    }

    /**
     * print request info
     *
     * @param request
     */
    public static void log(HttpServletRequest request) {

        RequestDTO dto = new RequestDTO();

        dto.setUri(request.getRequestURI());
        dto.setContentType(request.getContentType());
        dto.setMethod(request.getMethod());
        dto.setContentLength(request.getContentLength());
        dto.setQueryString(request.getQueryString());
        dto.setUserAgent(getUserAgent(request));

        log.info("request info={}", dto);
    }

    /**
     * print request parameter
     *
     * @param request
     */
    public static void logParameter(HttpServletRequest request) {
        Map<String, Object> map = getParameterMap(request);
        log.info("request parameter={}", map);
    }


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

        // note: we can read once!!
//        ContentCachingRequestWrapper wrapper = new ContentCachingRequestWrapper(request);
//
//        return new String(wrapper.getContentAsByteArray());

//        byte[] buf = wrapper.getContentAsByteArray();
//        if (buf.length > 0) {
//            try {
//                return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
//            } catch (UnsupportedEncodingException ex) {
//                return "[unknown]";
//            }
//        }


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

    /**
     * get 'User-Agent'
     *
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getHeader(HttpHeaderConst.USER_AGENT);
    }


    /**
     * 把request转为map
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getParameterMap(HttpServletRequest request) {

        Map<String, String[]> properties = request.getParameterMap();

        // 返回值Map
        Map<String, Object> returnMap = new HashMap<>();
        Iterator<?> entries = properties.entrySet().iterator();

        Map.Entry<String, Object> entry;
        String name = "";
        String value = "";
        Object valueObj = null;
        while (entries.hasNext()) {
            entry = (Map.Entry<String, Object>) entries.next();
            name = entry.getKey();
            valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }


    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};

    /**
     * get client ip address
     *
     * @param request
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

}
