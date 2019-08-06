package com.github.spy.sea.core.dubbo.legacy.filter;

import com.alibaba.dubbo.rpc.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Base App Exception Filter
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */
@Slf4j
public class BaseAppExceptionFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return null;
    }
}
