package com.github.spy.sea.core.spring.web.util;

import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.util.StringUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * request util in spring mvc
 *
 * @author spy
 * @version 1.0 2021/1/29
 * @since 1.0
 */
@Slf4j
public final class RequestUtil {

    private RequestUtil() {
    }

    /**
     * get request in spring mvc
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            ExceptionHandler.publishMsg("非Web上下文无法获取Request");
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * get param
     * priority HEADER> PARAMETER > COOKIE
     *
     * @param request http request
     * @param key     key
     * @return
     */
    public static String getParamByPriority(ServerHttpRequest request, String key) {
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
    public static String getParamByPriority(ServerHttpRequest request, String headerKey, String requestKey, String cookieKey) {
        if (StringUtil.isAllEmpty(headerKey, requestKey, cookieKey)) {
            return StringUtil.EMPTY;
        }

        // header
        String value = request.getHeaders().getFirst(headerKey);
        if (StringUtil.isEmpty(value)) {
            // request
            value = request.getQueryParams().getFirst(requestKey);
            if (StringUtil.isEmpty(value)) {
                // cookie
                HttpCookie cookie = request.getCookies().getFirst(cookieKey);
                value = cookie == null ? StringUtil.EMPTY : cookie.getValue();
            }
        }
        return value;
    }


    /**
     * print simple request info
     *
     * @param request
     */
    public static void logSimple(ServerHttpRequest request) {
        log.info("sea request log: [{},{}] id={},queryString={},userAgent={}",
                request.getMethodValue(), request.getPath(),
                request.getId(), request.getQueryParams(), getUserAgent(request));
    }

    public static String getUserAgent(ServerHttpRequest request) {
        if (request == null) {
            return null;
        }
        return request.getHeaders().getFirst(HttpHeaders.USER_AGENT);
    }
}
