package com.github.spy.sea.core.dubbo.filter;

import com.github.spy.sea.core.common.CoreErrorConst;
import com.github.spy.sea.core.exception.BaseAppException;
import com.github.spy.sea.core.model.BaseResult;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.*;
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

                BaseResult<Boolean> bizResult = BaseResult.fail();


                if (result.getException() instanceof BaseAppException) {
                    BaseAppException exception = (BaseAppException) result.getException();

                    bizResult.setErrorCode(exception.getCode());
                    bizResult.setErrorMessage(exception.getDesc());

                } else {
                    bizResult.setErrorCode(CoreErrorConst.RPC_INVOKE_ERR);
                }

                if (Strings.isNullOrEmpty(bizResult.getErrorMessage())) {
                    bizResult.setErrorMessage(getResMsg(bizResult.getErrorCode()));
                }

                return new AppResponse(bizResult);
            } else {

                // 没有异常部分，同时检测success=false字段
                if (result.getValue() instanceof BaseResult) {

                    BaseResult<Boolean> bizResult = (BaseResult) result.getValue();

                    if ((!bizResult.getSuccess()) && Strings.isNullOrEmpty(bizResult.getErrorMessage())) {
                        log.error("success=false and msg is null");
                        bizResult.setErrorMessage(getResMsg(bizResult.getErrorCode()));
                    }
                }
            }
            return result;
        } catch (Exception e) {
            log.error("rpc error", e);
            BaseResult<Boolean> result = new BaseResult<>();
            result.setSuccess(false);
            result.setData(false);
            result.setErrorCode(CoreErrorConst.RPC_INVOKE_ERR);
            result.setErrorMessage(getResMsg(CoreErrorConst.RPC_INVOKE_ERR));

            return new AppResponse(result);
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
