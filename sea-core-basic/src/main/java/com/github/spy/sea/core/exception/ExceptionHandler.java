package com.github.spy.sea.core.exception;

import com.github.spy.sea.core.util.StringUtil;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.InvocationTargetException;

/**
 * 模块名
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
     * 抛出异常
     *
     * @param errorCode
     * @return
     * @throws BaseAppException
     */
    public static BaseAppException publish(String errorCode) throws BaseAppException {
        return publish(errorCode, null, null);
    }

    public static BaseAppException publish(String errorCode, String msg) throws BaseAppException {
        return publish(errorCode, msg, null);
    }

    /**
     * 兼容已有系统
     *
     * @param errorCode
     * @return
     * @throws BaseAppException
     */
    public static BaseAppException publishOld(String errorCode) throws BaseAppException {
        errorCode = StringUtil.isEmpty(errorCode) ? OLD_CODE : (OLD_CODE + errorCode);
        return publish(errorCode, null, null);
    }

    public static BaseAppException publishOld(String errorCode, String msg) throws BaseAppException {
        errorCode = StringUtil.isEmpty(errorCode) ? OLD_CODE : (OLD_CODE + errorCode);
        return publish(errorCode, msg, null);
    }

    public static BaseAppException publishOld(Long errorCode) throws BaseAppException {
        String newErrorCode = errorCode == null ? OLD_CODE : (OLD_CODE + errorCode.toString());
        return publish(newErrorCode, null, null);
    }

    public static BaseAppException publishOld(Long errorCode, String msg) throws BaseAppException {
        String newErrorCode = errorCode == null ? OLD_CODE : (OLD_CODE + errorCode.toString());
        return publish(newErrorCode, msg, null);
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
     * @param errorCode String 错误码
     * @param msg       String 消息
     * @param t         Throwable
     * @return BaseAppException
     * @throws BaseAppException
     */
    public static BaseAppException publish(String errorCode, String msg, Throwable t) throws BaseAppException {

        BaseAppException baseAppException;
        if (t instanceof BaseAppException) {
            baseAppException = (BaseAppException) t;
        } else if (t instanceof InvocationTargetException) {
            // 仅仅对此情况进行处理，不能进行深层检查！
            Throwable cause = t.getCause();
            if (cause instanceof BaseAppException) {
                baseAppException = (BaseAppException) cause;
            } else {
                baseAppException = new BaseAppException(errorCode, msg);
            }
        } else {
            baseAppException = new BaseAppException(errorCode, msg);
        }

        throw baseAppException;
    }
}
