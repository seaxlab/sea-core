package com.github.spy.sea.core.spring.web.filter;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.extension.HttpCookieParseExtension;
import com.github.spy.sea.core.extension.HttpHeaderParseExtension;
import com.github.spy.sea.core.extension.HttpRequestParseExtension;
import com.github.spy.sea.core.loader.EnhancedServiceLoader;
import com.github.spy.sea.core.spring.web.util.RequestUtil;
import com.github.spy.sea.core.thread.ThreadContext;
import com.github.spy.sea.core.util.ListUtil;
import com.github.spy.sea.core.util.MapUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/29
 * @since 1.0
 */
@Slf4j
public class SeaGlobalWebFluxFilter implements WebFilter, Ordered {

    private static Map<String, String> httpHeaderMap;
    private static Map<String, String> httpCookieMap;
    private static Map<String, String> httpRequestMap;

    @PostConstruct
    public void init() {
        initHttpHeaderParse();
        initHttpCookieParse();
        initHttpRequestParse();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        RequestUtil.logSimple(request);
        parseHttpHeader(request);
        parseHttpRequest(request);
        parseHttpCookie(request);

        initCommon(request);
        return chain.filter(exchange).doFinally((signalType) -> {
            ThreadContext.clean();
        });
    }


    /**
     * init common info into thread context
     *
     * @param request
     */
    private void initCommon(ServerHttpRequest request) {
        ThreadContext.put(CoreConst.REQUEST_URL, request.getURI().toString());
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
    private void parseHttpHeader(ServerHttpRequest request) {
        if (MapUtil.isEmpty(httpHeaderMap)) {
            return;
        }
        for (Map.Entry<String, String> entry : httpHeaderMap.entrySet()) {
            String value = request.getHeaders().getFirst(entry.getKey());
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
    private void parseHttpCookie(ServerHttpRequest request) {
        if (MapUtil.isEmpty(httpCookieMap)) {
            return;
        }
        for (Map.Entry<String, String> entry : httpCookieMap.entrySet()) {
            HttpCookie cookie = request.getCookies().getFirst(entry.getKey());
            if (cookie == null) {
                return;
            }
            String value = cookie.getValue();
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
    private void parseHttpRequest(ServerHttpRequest request) {
        if (MapUtil.isEmpty(httpRequestMap)) {
            return;
        }
        for (Map.Entry<String, String> entry : httpRequestMap.entrySet()) {
            String value = request.getQueryParams().getFirst(entry.getKey());
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

    @Override
    public int getOrder() {
        return 1000;
    }
}
