package com.github.spy.sea.core.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.Serializable;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0 2019-05-13
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class BaseResult<T> implements Serializable {

    @JSONField(ordinal = 1030)
    @Builder.Default
    private Boolean success = true;

    @JSONField(ordinal = 1020)
    private String errorCode; // 错误编码

    private transient Object[] errorParam; //错误信息中占位符变量

    @JSONField(ordinal = 1010)
    private String errorMessage;// 错误信息

    @JSONField(ordinal = 1005)
    private String errorField; // 错误字段

    @JSONField(ordinal = 1000)
    private String errorType; // 错误类别

    @JSONField(ordinal = -990)
    private T data; // 默认返回结果

    //请求id
    @JSONField(ordinal = -980)
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
     * 直接返回true
     *
     * @return BaseResult
     */
    public static BaseResult success() {
        return new BaseResult(true, null);
    }

    public static <T> BaseResult success(T data) {
        return new BaseResult(true, null, data);
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
     *      result.setErrorMsg("{}拯救地球，{}!","xiaoming","yeah");
     *      //xiaoming拯救地球，yeah!
     *  </pre>
     *
     * @param format
     * @param argArray
     */
    public void setErrorMsg(String format, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        this.setErrorMessage(ft.getMessage());
    }


}
