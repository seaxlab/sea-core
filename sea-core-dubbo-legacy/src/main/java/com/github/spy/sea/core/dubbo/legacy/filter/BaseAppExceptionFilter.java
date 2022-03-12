package com.github.spy.sea.core.dubbo.legacy.filter;

import com.alibaba.dubbo.rpc.*;
import com.github.spy.sea.core.common.CoreErrorConst;
import com.github.spy.sea.core.exception.BaseAppException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * Base App Exception Filter
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */
@Slf4j
public class BaseAppExceptionFilter implements Filter {
    @Autowired
    private MessageSource messageSource;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {

            Result result = invoker.invoke(invocation);

            if (result.hasException()) {
                log.error("result has exception");

                com.github.spy.sea.core.model.Result<Boolean> bizResult = com.github.spy.sea.core.model.Result.fail();


                if (result.getException() instanceof BaseAppException) {
                    BaseAppException exception = (BaseAppException) result.getException();

                    bizResult.setCode(exception.getCode());
                    bizResult.setMsg(exception.getDesc());

                } else {
                    bizResult.setCode(CoreErrorConst.RPC_INVOKE_ERR);
                }

                if (Strings.isNullOrEmpty(bizResult.getMsg())) {
                    bizResult.setMsg(getResMsg(bizResult.getCode()));
                }

                return new RpcResult(bizResult);
            } else {

                // 没有异常部分，同时检测success=false字段
                if (result.getValue() instanceof com.github.spy.sea.core.model.Result) {

                    com.github.spy.sea.core.model.Result<Boolean> bizResult = (com.github.spy.sea.core.model.Result) result.getValue();

                    if ((!bizResult.getSuccess()) && Strings.isNullOrEmpty(bizResult.getMsg())) {
                        log.error("success=false and msg is null");
                        bizResult.setMsg(getResMsg(bizResult.getCode()));
                    }
                }
            }
            return result;
        } catch (Exception e) {
            log.error("rpc error", e);
            com.github.spy.sea.core.model.Result<Boolean> result = new com.github.spy.sea.core.model.Result<>();
            result.setSuccess(false);
            result.setData(false);
            result.setCode(CoreErrorConst.RPC_INVOKE_ERR);
            result.setMsg(getResMsg(CoreErrorConst.RPC_INVOKE_ERR));

            return new RpcResult(result);
        }
    }

    private String getResMsg(Long code) {
        if (code != null) {
            return getResMsg(code.toString(), null);
        }
        return null;
    }

    private String getResMsg(String code) {
        return getResMsg(code, null);
    }

    private String getResMsg(String code, Object[] args) {
        return messageSource.getMessage(code, args, code, Locale.CHINESE);
    }
}
