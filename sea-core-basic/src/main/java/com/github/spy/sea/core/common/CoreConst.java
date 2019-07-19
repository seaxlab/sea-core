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


    public static String REQUEST_ID = "requestId";

    /**
     * default charset name
     */
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * default charset is utf-8
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);


}
