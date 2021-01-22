package com.github.spy.sea.core.dubbo.filter;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.thread.ThreadContext;
import com.github.spy.sea.core.util.StringUtil;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * 注意这里的order顺序，暂时一定为2，有需要再调整
 * 主要考虑在异常之前，default和tracer之后
 *
 * @author spy
 * @version 1.0 2020/3/23
 * @since 1.0
 */
@Activate(group = CommonConstants.PROVIDER, order = 2)
public class SeaGlobalFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // support dubbo tag
        String dubboTag = invocation.getAttachment(CommonConstants.TAG_KEY);
        if (StringUtil.isNotEmpty(dubboTag)) {
            ThreadContext.put(CoreConst.CTX_TAG_GRAY, dubboTag);
        }

        Result ret;
        try {
            ret = invoker.invoke(invocation);
        } finally {
            ThreadContext.clean();
        }
        return ret;
    }
}
