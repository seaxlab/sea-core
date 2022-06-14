package com.github.seaxlab.core.common;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/13
 * @since 1.0
 */
@Slf4j
public class MsgConst {

    private MsgConst() {
    }

    public static final String CODE_IS_NULL = "code is null.";

    // 锁号失败文案
    public static final String LOCK_FAIL_COMMON_DESC = "操作进行中，请稍后尝试。";

}
