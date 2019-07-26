package com.github.spy.sea.core.exception;

import com.github.spy.sea.core.common.CoreErrorConst;
import lombok.extern.slf4j.Slf4j;

/**
 * 不支持的操作
 *
 * @author spy
 * @version 1.0 2019-07-26
 * @since 1.0
 */
@Slf4j
public class UnsupportedOperationException extends BaseAppException {


    public UnsupportedOperationException() {
        super(CoreErrorConst.UNSUPPORT_OPERATION, "不支持的操作");
    }


    public UnsupportedOperationException(String message) {
        super(CoreErrorConst.UNSUPPORT_OPERATION, message);
    }


//    public UnsupportedOperationException(String message, Throwable cause) {
//        super(message, cause);
//    }


}
