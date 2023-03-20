package com.github.seaxlab.core.model;


import com.github.seaxlab.core.enums.IErrorEnum;
import java.io.Serializable;
import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * Result model
 *
 * @author spy
 * @version 1.0 2022/01/20
 * @since 1.0
 */
@Data
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
    this.code = "";
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
    this.code = "";
    this.msg = "";
    this.data = data;
  }

  /**
   * 直接返回true
   *
   * @return Result
   */
  public static <T> Result<T> success() {
    return new Result<>(true, null);
  }

  public static <T> Result<T> success(T data) {
    return new Result<>(true, null, data);
  }

  /**
   * success msg
   *
   * @param msg msg
   * @return base result
   */
  public static <T> Result<T> successMsg(String msg) {
    return new Result<>(true, msg);
  }

  /**
   * success msg
   *
   * @param code code
   * @param msg  msg
   * @return base result
   */
  public static <T> Result<T> successMsg(String code, String msg) {
    Result<T> result = new Result<>();
    result.setSuccess(true);
    result.setCode(code);
    result.setMsg(msg);
    return result;
  }

  public static <T> Result<T> fail() {
    return fail("", "");
  }

  public static <T> Result<T> failMsg(String msg) {
    return fail(null, msg);
  }

  private static <T> Result<T> fail(String code, String msg) {
    Result<T> result = new Result<>();

    result.setSuccess(false);
    result.setCode(code);
    result.setMsg(msg);
    return result;
  }

  public static <T> Result<T> fail(IErrorEnum errorEnum) {
    Result<T> result = new Result<>();
    result.setSuccess(false);
    result.setCode(errorEnum.getCode());
    result.setMsg(errorEnum.getMessage());
    return result;
  }

  public static <T> Result<T> failF(IErrorEnum errorEnum, Object... args) {
    Result<T> result = new Result<>();
    result.setSuccess(false);
    result.setCode(errorEnum.getCode());
    result.setMsg(MessageFormatter.arrayFormat(errorEnum.getMessage(), args).getMessage());
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

  public void from(Result<T> otherResult) {
    if (otherResult == null) {
      return;
    }
    this.code = otherResult.getCode();
    this.msg = otherResult.getMsg();
  }


}
