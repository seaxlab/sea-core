package com.github.seaxlab.core.web.filter;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.loader.EnhancedServiceLoader;
import com.github.seaxlab.core.thread.ThreadContext;
import com.github.seaxlab.core.util.ListUtil;
import com.github.seaxlab.core.util.MapUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.web.common.WebConst;
import com.github.seaxlab.core.web.extension.HttpCookieParseExtension;
import com.github.seaxlab.core.web.extension.HttpHeaderParseExtension;
import com.github.seaxlab.core.web.extension.HttpRequestParseExtension;
import com.github.seaxlab.core.web.util.CookieUtil;
import com.github.seaxlab.core.web.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sea global filter
 *
 * @author spy
 * @version 1.0 2020/3/23
 * @since 1.0
 */
@Slf4j
public class SeaGlobalFilter implements Filter {

    private static String logMode;
    private static Map<String, String> httpHeaderMap;
    private static Map<String, String> httpCookieMap;
    private static Map<String, String> httpRequestMap;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("sea global filter init");

        logMode = StringUtil.defaultIfBlank(filterConfig.getInitParameter(WebConst.FILTER_CONFIG_LOG_MODE), WebConst.LOG_MODE_1);

        initHttpHeaderParse();
        initHttpCookieParse();
        initHttpRequestParse();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            logRequest(req);
            parseHttpHeader(req);
            parseHttpCookie(req);
            parseHttpRequest(req);

            initCommon(req);
            chain.doFilter(request, response);
        } finally {
            ThreadContext.clean();
        }
    }


    @Override
    public void destroy() {
        // no-op
    }

    private void logRequest(HttpServletRequest request) {
        switch (logMode) {
            default:
            case WebConst.LOG_MODE_1:
                RequestUtil.logSimple(request);
                break;
            case WebConst.LOG_MODE_2:
                RequestUtil.logSimple2(request);
                break;
            case WebConst.LOG_MODE_3:
                RequestUtil.logSimple3(request);
                break;
        }
    }

    /**
     * init common info into thread context
     *
     * @param request
     */
    private void initCommon(HttpServletRequest request) {
        ThreadContext.put(CoreConst.REQUEST_URL, request.getRequestURI());
        ThreadContext.put(CoreConst.REQUEST_USER_AGENT, RequestUtil.getUserAgent(request));
    }

    /**
     * which key should parse.
     */
    private void initHttpHeaderParse() {

        List<HttpHeaderParseExtension> extensionList = EnhancedServiceLoader.loadAll(HttpHeaderParseExtension.class);
        log.info("Http header parse extension count={}", extensionList.size());

        if (ListUtil.isNotEmpty(extensionList)) {
            httpHeaderMap = new HashMap<>();
            for (HttpHeaderParseExtension extension : extensionList) {
                Map<String, String> map = extension.get();
                httpHeaderMap.putAll(map);
            }
        } else {
            httpHeaderMap = MapUtil.empty();
        }
    }

    /**
     * parse cookie extension.
     */
    private void initHttpCookieParse() {
        List<HttpCookieParseExtension> extensionList = EnhancedServiceLoader.loadAll(HttpCookieParseExtension.class);
        log.info("Http cookie parse extension count={}", extensionList.size());

        if (ListUtil.isNotEmpty(extensionList)) {
            httpCookieMap = new HashMap<>();
            for (HttpCookieParseExtension extension : extensionList) {
                Map<String, String> map = extension.get();
                httpCookieMap.putAll(map);
            }
        } else {
            httpCookieMap = MapUtil.empty();
        }
    }

    /**
     * which key parameter should parse.
     */
    private void initHttpRequestParse() {
        List<HttpRequestParseExtension> extensionList = EnhancedServiceLoader.loadAll(HttpRequestParseExtension.class);
        log.info("Http request parse extension count={}", extensionList.size());

        if (ListUtil.isNotEmpty(extensionList)) {
            httpRequestMap = new HashMap<>();
            for (HttpRequestParseExtension extension : extensionList) {
                Map<String, String> map = extension.get();
                httpRequestMap.putAll(map);
            }
        } else {
            httpRequestMap = MapUtil.empty();
        }
    }


    /**
     * parse specified http header into thread context.
     *
     * @param request
     */
    private void parseHttpHeader(HttpServletRequest request) {
        if (MapUtil.isEmpty(httpHeaderMap)) {
            return;
        }
        for (Map.Entry<String, String> entry : httpHeaderMap.entrySet()) {
            String value = request.getHeader(entry.getKey());
            if (StringUtil.isNotEmpty(value)) {
                ThreadContext.putIfAbsent(entry.getValue(), value);
                if (log.isDebugEnabled()) {
                    log.debug("put [{}->{}={}] into thread context", entry.getKey(), entry.getValue(), value);
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("value of [{}] is null in http header", entry.getKey());
                }
            }
        }
    }

    /**
     * parse specified http cookie into thread context.
     *
     * @param request
     */
    private void parseHttpCookie(HttpServletRequest request) {
        if (MapUtil.isEmpty(httpCookieMap)) {
            return;
        }
        for (Map.Entry<String, String> entry : httpCookieMap.entrySet()) {
            String value = CookieUtil.get(request, entry.getKey());
            if (StringUtil.isNotEmpty(value)) {
                ThreadContext.putIfAbsent(entry.getValue(), value);
                if (log.isDebugEnabled()) {
                    log.debug("put [{}->{}={}] into thread context", entry.getKey(), entry.getValue(), value);
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("value of [{}] is null in http cookie", entry.getKey());
                }
            }
        }
    }

    /**
     * parse specified http request param into thread context
     *
     * @param request
     */
    private void parseHttpRequest(HttpServletRequest request) {
        if (MapUtil.isEmpty(httpRequestMap)) {
            return;
        }
        for (Map.Entry<String, String> entry : httpRequestMap.entrySet()) {
            String value = request.getParameter(entry.getKey());
            if (StringUtil.isNotEmpty(value)) {
                ThreadContext.putIfAbsent(entry.getValue(), value);
                if (log.isDebugEnabled()) {
                    log.debug("put [{}->{}={}] into thread context", entry.getKey(), entry.getValue(), value);
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("value of [{}] is null in http request", entry.getKey());
                }
            }
        }
    }
}
