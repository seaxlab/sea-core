package com.github.spy.sea.core.web.filter;

import com.github.spy.sea.core.extension.HttpHeaderParseExtension;
import com.github.spy.sea.core.extension.HttpRequestParseExtension;
import com.github.spy.sea.core.loader.EnhancedServiceLoader;
import com.github.spy.sea.core.thread.ThreadContext;
import com.github.spy.sea.core.util.ListUtil;
import com.github.spy.sea.core.util.MapUtil;
import com.github.spy.sea.core.util.StringUtil;
import com.github.spy.sea.core.web.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/23
 * @since 1.0
 */
@Slf4j
public class SeaGlobalFilter implements Filter {

    private static Map<String, String> httpHeaderMap;
    private static Map<String, String> httpRequestMap;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("sea global filter init");

        initHttpHeaderParse();
        initHttpRequestParse();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            logRequest((HttpServletRequest) request);
            parseHttpHeader((HttpServletRequest) request);
            parseHttpRequest((HttpServletRequest) request);
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
        RequestUtil.logSimple(request);
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
                log.warn("value of [{}] is null in http header", entry.getKey());
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
                log.warn("value of [{}] is null in http header", entry.getKey());
            }
        }
    }
}
