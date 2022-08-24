package com.github.seaxlab.core.model.common;

import lombok.extern.slf4j.Slf4j;

/**
 * Api prefix const
 *
 * @author spy
 * @version 1.0 2022/4/20
 * @since 1.0
 */
@Slf4j
public class ApiConst {

    private ApiConst() {
    }

    // 公开API
    public static final String OPEN = "/api/open";

    // 内部API
    public static final String INNER = "/api/inner";

    // for console
    public static final String CONSOLE = "/api/console";

    // for app
    public static final String APP = "/api/app";

    // for test
    public static final String TEST = "/api/test";

}
