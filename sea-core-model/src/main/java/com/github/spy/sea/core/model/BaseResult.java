package com.github.spy.sea.core.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

import java.io.Serializable;

/**
 * 返回值模型
 *
 * @author spy
 * @version 1.0 2019-05-13
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class BaseResult<T> implements Serializable {

    /**
     * 调用结果
     */
    @JSONField(ordinal = 1000)
    @Builder.Default
    private Boolean success = true;

    /**
     * 链路id
     */
    @JSONField(ordinal = 1010)
    private String traceId;

    /**
     * 当前系统节点id
     */
    @JSONField(ordinal = 1011)
    private String spanId;

    /**
     * 错误编码
     */
    @JSONField(ordinal = 1020)
    private String errorCode;

    /**
     * 错误信息中占位符变量
     */
    private transient Object[] errorParam;

    /**
     * 错误信息
     */
    @JSONField(ordinal = 1030)
    private String errorMessage;

    /**
     * 错误字段
     */
    @JSONField(ordinal = 1040)
    private String errorField;

    /**
     * 错误类别
     */
    @JSONField(ordinal = 1050)
    private String errorType;

    /**
     * 默认返回结果
     */
    @JSONField(ordinal = 1060)
    private T data;

    /**
     * 扩展参数，如果data中无法存放时才使用此参数
     */
    @JSONField(ordinal = 1070)
    private Object extra;

    /**
     * 请求id
     * 请使用traceId
     *
     * @deprecated 请使用traceId
     */
    @JSONField(ordinal = -980)
    @Deprecated
    private String requestId;


    public BaseResult() {
        this(true, null);
    }

    public BaseResult(boolean success) {
        this(success, null, null);
    }

    public BaseResult(boolean success, String errorMessage) {
        this(success, errorMessage, null);
    }

    public BaseResult(boolean success, String errorMessage, T data) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    /**
     * set value, mark success is true, clean error code and error message.
     *
     * @param data return obj
     */
    public void value(T data) {
        this.success = true;
        this.data = data;
        this.errorMessage = "";
        this.errorCode = "";
    }

    /**
     * extra obj for special case
     *
     * @param obj extra obj.
     */
    public void extra(Object obj) {
        this.extra = obj;
    }

    /**
     * 直接返回true
     *
     * @return BaseResult
     */
    public static BaseResult success() {
        return new BaseResult(true, null);
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<T>(true, null, data);
    }

    /**
     * success msg
     *
     * @param message msg
     * @return base result
     */
    public static BaseResult successMsg(String message) {
        return new BaseResult(true, message);
    }

    /**
     * success msg
     *
     * @param code         code
     * @param errorMessage msg
     * @return base result
     */
    public static BaseResult successMsg(String code, String errorMessage) {
        return new BaseResult(true, code, errorMessage);
    }

    public static BaseResult fail() {
        return fail(null);
    }

    public static BaseResult fail(String errorCode) {
        return fail(errorCode, null);
    }

    public static BaseResult failMsg(String errorMessage) {
        return fail(null, errorMessage);
    }

    public static BaseResult fail(String errorCode, String errorMessage) {
        BaseResult result = new BaseResult();

        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;

    }

    /**
     * 格式化信息
     * <pre>
     *      result.setErrorMsg("{}拯救地球，{}!","大卫","yeah");
     *      //大卫拯救地球，yeah!
     *  </pre>
     *
     * @param format message pattern
     * @param args   param
     */
    public void setErrorMsg(String format, Object... args) {
        this.setErrorMessage(MessageFormatter.arrayFormat(format, args).getMessage());
    }

    /**
     * set format msg.
     *
     * @param format message pattern
     * @param args   param
     */
    public void setMsgF(String format, Object... args) {
        this.setErrorMessage(MessageFormatter.arrayFormat(format, args).getMessage());
    }

    /**
     * check is ok.
     *
     * @return true/false
     */
    @JSONField(serialize = false, deserialize = false)
    public boolean isOk() {
        if (success != null) {
            return success;
        }
        // warning!! default is false.
        return false;
    }

    /**
     * check is fail.
     *
     * @return true/false
     */
    @JSONField(serialize = false, deserialize = false)
    public boolean isFail() {
        if (success != null) {
            return !success;
        }
        return true;
    }


}
