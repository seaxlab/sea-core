package com.github.seaxlab.core.web.util;

import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.http.common.HttpHeaderConst;
import com.github.seaxlab.core.util.IOUtil;
import com.github.seaxlab.core.util.RandomUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.web.common.WebConst;
import com.github.seaxlab.core.web.model.RequestDTO;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    private RequestUtil() {
    }

    public enum Priority {
        REQUEST_HEADER_COOKIE,
        REQUEST_COOKIE_HEADER,
        HEADER_REQUEST_COOKIE,
        HEADER_COOKIE_REQUEST,
        COOKIE_REQUEST_HEADER,
        COOKIE_HEADER_REQUEST
    }

    /**
     * 按priority顺序进行取值
     *
     * @param request
     * @param priority
     * @param key
     * @return
     */
    public static String getParamByPriority(HttpServletRequest request, Priority priority, String key) {
        return getParamByPriority(request, priority, key, key, key);
    }

    /**
     * 按priorty 顺序进行取值
     *
     * @param request
     * @param priority
     * @param key1
     * @param key2
     * @param key3
     * @return
     */
    public static String getParamByPriority(HttpServletRequest request, Priority priority, String key1, String key2, String key3) {
        Preconditions.checkNotNull(request, "request cannot be null");
        Preconditions.checkNotNull(priority, "priority cannot be null");
        Preconditions.checkNotNull(key1, "key1 cannot be null");
        Preconditions.checkNotNull(key2, "key2 cannot be null");
        Preconditions.checkNotNull(key3, "key3 cannot be null");

        String value = "";
        switch (priority) {
            case REQUEST_HEADER_COOKIE:
                value = request.getParameter(key1);
                if (StringUtil.isEmpty(value)) {
                    value = request.getHeader(key2);
                    if (StringUtil.isEmpty(value)) {
                        value = CookieUtil.get(request, key3);
                    }
                }
                break;
            case REQUEST_COOKIE_HEADER:
                value = request.getParameter(key1);
                if (StringUtil.isEmpty(value)) {
                    value = CookieUtil.get(request, key2);
                    if (StringUtil.isEmpty(value)) {
                        value = request.getHeader(key3);
                    }
                }
                break;
            case HEADER_REQUEST_COOKIE:
                value = request.getHeader(key1);
                if (StringUtil.isEmpty(value)) {
                    value = request.getParameter(key2);
                    if (StringUtil.isEmpty(value)) {
                        value = CookieUtil.get(request, key3);
                    }
                }
                break;
            case HEADER_COOKIE_REQUEST:
                value = request.getHeader(key1);
                if (StringUtil.isEmpty(value)) {
                    value = CookieUtil.get(request, key2);
                    if (StringUtil.isEmpty(value)) {
                        value = request.getHeader(key3);
                    }
                }
                break;
            case COOKIE_REQUEST_HEADER:
                value = CookieUtil.get(request, key1);
                if (StringUtil.isEmpty(value)) {
                    value = request.getParameter(key2);
                    if (StringUtil.isEmpty(value)) {
                        value = request.getHeader(key3);
                    }
                }

                break;
            case COOKIE_HEADER_REQUEST:
                value = CookieUtil.get(request, key1);
                if (StringUtil.isEmpty(value)) {
                    value = request.getHeader(key2);
                    if (StringUtil.isEmpty(value)) {
                        value = request.getParameter(key3);
                    }
                }
                break;
        }

        return StringUtil.defaultIfEmpty(value, StringUtil.EMPTY);
    }

    /**
     * get param
     * priority HEADER> PARAMETER > COOKIE
     *
     * @param request http request
     * @param key     key
     * @return
     */
    public static String getParamByPriority(HttpServletRequest request, String key) {
        Preconditions.checkNotNull(key, "key不能为空");

        return getParamByPriority(request, key, key, key);
    }

    /**
     * get param by priority
     * header -> request -> cookie
     *
     * @param request    http request
     * @param headerKey  header key
     * @param requestKey request key
     * @param cookieKey  cookie key.
     * @return
     */
    public static String getParamByPriority(HttpServletRequest request, String headerKey, String requestKey, String cookieKey) {
        if (StringUtil.isAllEmpty(headerKey, requestKey, cookieKey)) {
            return StringUtil.EMPTY;
        }

        // header
        String value = request.getHeader(headerKey);
        if (StringUtil.isEmpty(value)) {
            // request
            value = request.getParameter(requestKey);
            if (StringUtil.isEmpty(value)) {
                // cookie
                value = CookieUtil.get(request, cookieKey);
            }
        }
        return value;
    }

    /**
     * request-->header--> cookie
     *
     * @param request
     * @param key
     * @return
     */
    public static String getParamByPriority2(HttpServletRequest request, String key) {
        return getParamByPriority2(request, key, key, key);
    }

    /**
     * request-->header--> cookie
     *
     * @param request
     * @param requestKey
     * @param headerKey
     * @param cookieKey
     * @return
     */
    public static String getParamByPriority2(HttpServletRequest request, String requestKey, String headerKey, String cookieKey) {
        if (StringUtil.isAllEmpty(headerKey, requestKey, cookieKey)) {
            return StringUtil.EMPTY;
        }

        // request
        String value = request.getParameter(requestKey);
        if (StringUtil.isEmpty(value)) {
            // header
            value = request.getHeader(headerKey);
            if (StringUtil.isEmpty(value)) {
                // cookie
                value = CookieUtil.get(request, cookieKey);
            }
        }
        return value;
    }


    /**
     * print simple request info
     *
     * @param request
     */
    public static void logSimple(HttpServletRequest request) {
        //TODO if you have invokerAppVersion/invokerChannel/invokerDeviceType, you can add more.
        log.info("sea request log: [{},{}] queryString={},userAgent={},ip={}",
                request.getMethod(), request.getRequestURI(), request.getQueryString(),
                getUserAgent(request), getClientIpAddress(request));
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
     * 读取request body 转换成Map
     *
     * @param request http servlet request
     * @return map
     */
    public static Map<String, String> getRequestBodyAsMap(final HttpServletRequest request) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String str = "";
            StringBuilder strBuilder = new StringBuilder();
            //一行一行的读取body体里面的内容；
            while ((str = reader.readLine()) != null) {
                strBuilder.append(str);
            }

            //转化成json对象
            return JSONObject.parseObject(strBuilder.toString(), Map.class);
        } catch (Exception e) {
            log.error("fail to request body.", e);
        }
        return new HashMap<>();
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
            return "";
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


    /**
     * get client ip address
     *
     * @param request
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        try {
            for (String header : WebConst.IP_HEADER_CANDIDATES) {
                String ip = request.getHeader(header);
                if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                    return ip;
                }
            }
            return request.getRemoteAddr();
        } catch (Exception e) {
            log.error("fail to get client ip", e);
        }
        return "";
    }

}
