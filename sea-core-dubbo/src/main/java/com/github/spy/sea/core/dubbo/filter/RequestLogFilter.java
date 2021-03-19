package com.github.spy.sea.core.dubbo.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.*;

/**
 * request log filter
 * <p>
 * 1. log request
 * </p>
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */
@Slf4j
public class RequestLogFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Object[] args = invocation.getArguments();

        log.info("调用DUBBO方法:{}.{}, args={}", invoker.getInterface().getName(), invocation.getMethodName(),
                JSON.toJSONString(args));

        return invoker.invoke(invocation);
    }
}
