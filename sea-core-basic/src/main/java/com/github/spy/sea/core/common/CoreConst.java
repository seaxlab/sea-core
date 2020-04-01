package com.github.spy.sea.core.common;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0 2019-05-14
 * @since 1.0
 */
@Slf4j
public class CoreConst {

    public static String SYS_OPERATOR = "SYS_OPERATOR";

    public static String SYS_CREATOR = "SYS_CREATOR";

    public static String SYS_EDITOR = "SYS_EDITOR";


    public static Boolean YES = Boolean.TRUE;

    public static Boolean NO = Boolean.FALSE;


    /**
     * 请求Id
     */
    public static String REQUEST_ID = "requestId";

    /**
     * 链路id
     */
    public static String TRACE_ID = "traceId";

    /**
     * MDC req.id
     */
    public static String MDC_REQ_ID = "req.id";


    /**
     * default charset name
     */
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * default charset is utf-8
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);

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

}
