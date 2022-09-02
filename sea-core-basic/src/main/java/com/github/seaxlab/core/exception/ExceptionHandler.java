package com.github.seaxlab.core.exception;

import com.github.seaxlab.core.enums.IErrorEnum;
import com.github.seaxlab.core.util.StringUtil;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.InvocationTargetException;

/**
 * 异常抛出辅助类
 *
 * @author spy
 * @version 1.0 2018/4/11
 * @since 1.0
 */
public final class ExceptionHandler {

    private ExceptionHandler() {
    }

    public static final String NO_ERROR_CODE = "";

    /**
     * 兼容已有系统
     */
    public static final String OLD_CODE = "OLD_";


    /**
     * publish BaseAppException from IErrorException
     *
     * @param exception IErrorException
     * @return
     */
    public static BaseAppException publish(IErrorEnum exception) {
        throw new BaseAppException(exception);
    }

    /**
     * publish BaseAppException from IErrorException
     *
     * @param exception IErrorException
     * @param args      arguments
     * @return
     */
    public static BaseAppException publish(IErrorEnum exception, Object... args) {
        throw new BaseAppException(exception, args);
    }

    /**
     * 抛出异常
     *
     * @param code
     * @return BaseAppException
     * @throws BaseAppException
     */
    public static BaseAppException publish(String code) throws BaseAppException {
        return publish(code, null, null);
    }

    public static BaseAppException publish(String code, String msg) throws BaseAppException {
        return publish(code, msg, null);
    }

    /**
     * 兼容已有系统
     *
     * @param code
     * @return BaseAppException
     * @throws BaseAppException
     */
    public static BaseAppException publishOld(String code) throws BaseAppException {
        code = StringUtil.isEmpty(code) ? OLD_CODE : (OLD_CODE + code);
        return publish(code, null, null);
    }

    public static BaseAppException publishOld(String code, String msg) throws BaseAppException {
        code = StringUtil.isEmpty(code) ? OLD_CODE : (OLD_CODE + code);
        return publish(code, msg, null);
    }

    public static BaseAppException publishOld(Long code) throws BaseAppException {
        String newCode = code == null ? OLD_CODE : (OLD_CODE + code.toString());
        return publish(newCode, null, null);
    }

    public static BaseAppException publishOld(Long code, String msg) throws BaseAppException {
        String newCode = code == null ? OLD_CODE : (OLD_CODE + code.toString());
        return publish(newCode, msg, null);
    }

    public static BaseAppException publishMsgOld(String msg) throws BaseAppException {
        return publish(OLD_CODE, msg, null);
    }

    public static BaseAppException publishMsg(String msg) throws BaseAppException {
        return publish(NO_ERROR_CODE, msg, null);
    }

    public static BaseAppException publishMsg(String format, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        return publish(NO_ERROR_CODE, ft.getMessage(), null);
    }

    /**
     * 抛出异常
     *
     * @param code 错误码
     * @param msg  消息
     * @param t    Throwable
     * @return BaseAppException
     * @throws BaseAppException
     */
    public static BaseAppException publish(String code, String msg, Throwable t) throws BaseAppException {

        BaseAppException baseAppException;
        if (t instanceof BaseAppException) {
            baseAppException = (BaseAppException) t;
        } else if (t instanceof InvocationTargetException) {
            // 仅仅对此情况进行处理，不能进行深层检查！
            Throwable cause = t.getCause();
            if (cause instanceof BaseAppException) {
                baseAppException = (BaseAppException) cause;
            } else {
                baseAppException = new BaseAppException(code, msg);
            }
        } else {
            baseAppException = new BaseAppException(code, msg);
        }

        throw baseAppException;
    }
}
