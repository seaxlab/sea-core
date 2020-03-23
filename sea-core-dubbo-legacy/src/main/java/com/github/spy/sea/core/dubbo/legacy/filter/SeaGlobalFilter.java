package com.github.spy.sea.core.dubbo.legacy.filter;

import com.alibaba.dubbo.rpc.*;
import com.github.spy.sea.core.thread.ThreadContext;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/23
 * @since 1.0
 */
public class SeaGlobalFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result ret;
        try {
            ret = invoker.invoke(invocation);
        } finally {
            ThreadContext.clean();
        }
        return ret;
    }
}
