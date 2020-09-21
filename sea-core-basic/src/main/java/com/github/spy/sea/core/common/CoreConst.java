package com.github.spy.sea.core.common;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0 2019-05-14
 * @since 1.0
 */
@Slf4j
public class CoreConst {
    private CoreConst() {
    }

    public static final String SYS_OPERATOR = "SYS_OPERATOR";

    public static final String SYS_CREATOR = "SYS_CREATOR";

    public static final String SYS_EDITOR = "SYS_EDITOR";


    public static final Boolean YES = Boolean.TRUE;

    public static final Boolean NO = Boolean.FALSE;


    /**
     * 请求Id
     */
    public static final String REQUEST_ID = "requestId";

    /**
     * 链路id
     */
    public static final String TRACE_ID = "traceId";

    /**
     * MDC req.id
     */
    public static final String MDC_REQ_ID = "req.id";


    /**
     * default charset name
     */
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * default charset is utf-8
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * http
     */
    public static final String SCHEME_HTTP = "http";

    /**
     * https
     */
    public static final String SCHEME_HTTPS = "https";

    /**
     * default page size
     */
    public static final int DEFAULT_PAGE_SIZE = 200;

    /**
     * 程序当前运行模式
     */
    public static final String KEY_SEA_DEV_MODE = "sea.dev.mode";

    /**
     * 灰度标识
     */
    public static final String TAG_GRAY = "sea-tag-gray";

    /**
     * 压力测试标识
     */
    public static final String TAG_STRESS_TESTING = "sea-tag-stress-testing";

    public static final String REQUEST_URL = "request.url";
    public static final String REQUEST_USER_AGENT = "request.user.agent";

    /**
     * you should config [com.github.spy.sea.core.web.servlet.WebApplicationListener] in web application.
     */
    public static String WEB_ROOT = "";

    /*1KB*/
    public static long ONE_KB = 1024;

    /*1MB*/
    public static long ONE_MB = ONE_KB * 1024;

    /*1GB*/
    public static long ONE_GB = ONE_MB * 1024;
}
