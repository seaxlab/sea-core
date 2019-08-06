package com.github.spy.sea.core.dubbo.legacy.filter;

import com.alibaba.dubbo.rpc.*;
import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * MDC filter
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */
@Slf4j
public class MDCFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        try {
            if (StringUtils.isEmpty(MDC.get(CoreConst.MDC_REQ_ID))) {
                MDC.put(CoreConst.MDC_REQ_ID, IdUtil.shortUUID());
            }
            return invoker.invoke(invocation);
        } finally {
            MDC.clear();
        }
    }
}
