package com.github.seaxlab.core.serialize.exception;

import com.github.seaxlab.core.common.CoreErrorConst;
import com.github.seaxlab.core.exception.BaseAppException;
import lombok.extern.slf4j.Slf4j;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-26
 * @since 1.0
 */
@Slf4j
public class SerializeException extends BaseAppException {

    public SerializeException() {
        super(CoreErrorConst.SERIAL_ERR, "序列化异常");
    }
}
