package com.github.seaxlab.core.web.common;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/3
 * @since 1.0
 */
@Slf4j
public class WebConst {

    public static final String[] IP_HEADER_CANDIDATES = { //
            "X-Forwarded-For", //
            "Proxy-Client-IP", //
            "WL-Proxy-Client-IP", //
            "HTTP_X_FORWARDED_FOR", //
            "HTTP_X_FORWARDED", //
            "HTTP_X_CLUSTER_CLIENT_IP", //
            "HTTP_CLIENT_IP", //
            "HTTP_FORWARDED_FOR", //
            "HTTP_FORWARDED", //
            "HTTP_VIA", //
            "REMOTE_ADDR"};

    // log mode in filter init config
    public static final String FILTER_CONFIG_LOG_MODE = "logMode";

    public static final String LOG_MODE_1 = "1";
    public static final String LOG_MODE_2 = "2";
}
