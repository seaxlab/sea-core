package com.github.spy.sea.core.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

import java.io.Serializable;

/**
 * 返回值模型
 *
 * @author spy
 * @version 1.0 2022/01/20
 * @since 1.0
 */
@Data
@JSONType(orders = {"success", "traceId", "code", "msg", "data", "extra"})
public class Result<T> implements Serializable {

    /**
     * 调用结果
     */
    private Boolean success;

    /**
     * 链路id
     */
    private String traceId;

    /**
     * 错误编码
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 默认返回结果
     */
    private T data;

    /**
     * 扩展参数，如果data中无法存放时才使用此参数
     */
    private Object extra;


    public Result() {
        this(true, null);
    }

    public Result(boolean success) {
        this(success, null, null);
    }

    public Result(boolean success, String msg) {
        this(success, msg, null);
    }

    public Result(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
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
        this.msg = "";
        this.code = "";
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
    public static Result success() {
        return new Result(true, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, null, data);
    }

    /**
     * success msg
     *
     * @param msg msg
     * @return base result
     */
    public static Result successMsg(String msg) {
        return new Result(true, msg);
    }

    /**
     * success msg
     *
     * @param code code
     * @param msg  msg
     * @return base result
     */
    public static Result successMsg(String code, String msg) {
        return new Result(true, code, msg);
    }

    public static Result fail() {
        return fail(null);
    }

    public static Result fail(String code) {
        return fail(code, null);
    }

    public static Result failMsg(String msg) {
        return fail(null, msg);
    }

    public static Result fail(String code, String msg) {
        Result result = new Result();

        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * set format msg.
     *
     * @param format message pattern
     * @param args   param
     */
    public void setMsgF(String format, Object... args) {
        this.setMsg(MessageFormatter.arrayFormat(format, args).getMessage());
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

    public void from(Result otherResult) {
        if (otherResult == null) {
            return;
        }
        this.code = otherResult.getCode();
        this.msg = otherResult.getMsg();
    }


}
