package com.github.spy.sea.core.web.controller;


import com.alipay.common.tracer.core.holder.SofaTraceContextHolder;
import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.common.CoreErrorConst;
import com.github.spy.sea.core.enums.ErrorTypeEnum;
import com.github.spy.sea.core.exception.BaseAppException;
import com.github.spy.sea.core.model.Result;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;


/**
 * Global异常
 *
 * @author spy
 * @date 2020-06-03
 */
@Slf4j
//@ControllerAdvice(basePackages = {"com.xx.console", "com.xx.admin"})
public abstract class AbstractGlobalExceptionHandler {

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected HttpServletRequest request;


    @ExceptionHandler(value = BaseAppException.class)
    @ResponseBody
    public ResponseEntity<Result> handleBaseAppException(BaseAppException e) {
        log.error("Biz service Exception", e);

        return new ResponseEntity<>(buildResult(String.valueOf(e.getCode()), ErrorTypeEnum.BIZ.getCode(),
                e.getDesc(), null), HttpStatus.OK);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Result> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Biz service Exception", e);

        return new ResponseEntity<Result>(buildResult(String.valueOf(-1), ErrorTypeEnum.BIZ.getCode(),
                e.getMessage(), null), HttpStatus.OK);
    }

    private Result buildResult(String errorCode, String errorType, String defaultErrorMsg, Object data) {
        Result result = Result.fail(errorCode);

        result.setCode(errorCode);
//        result.setErrorType(errorType);
        result.setMsg(getErrorMessage(errorCode, defaultErrorMsg));

        log.error("errorCode={},errorMessage={}", result.getCode(), result.getMsg());

        printHttpHeader(request);

        return result;
    }

    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    public ResponseEntity<?> handleHttpMediaTypeNotAcceptableException(HttpServletRequest request,
                                                                       HttpMediaTypeNotAcceptableException be) {
        log.error("handle HttpMediaTypeNotAcceptableException", be);

        return new ResponseEntity<>(buildBaseResult(request, CoreErrorConst.SYS_EXCEPTION, be.getMessage(),
                ErrorTypeEnum.SYSTEM.getCode(), getMessage(be)), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                                          HttpRequestMethodNotSupportedException be) {
        log.error("handle HttpRequestMethodNotSupportedException", be);

        return new ResponseEntity<>(buildBaseResult(request, CoreErrorConst.SYS_EXCEPTION, be.getMessage(),
                ErrorTypeEnum.SYSTEM.getCode(), getMessage(be)), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            TypeMismatchException.class,
            ServletRequestBindingException.class,
            BindException.class})
    @ResponseBody
    public ResponseEntity<Result> handleExceptions(HttpServletRequest request, Exception e) {

        log.error("handle Exceptions", e);

        String msg = e.getMessage();

        String errorField = null;

        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            if (bindException.hasErrors()) {
                List<FieldError> fieldErrors = bindException.getFieldErrors();
                if (!CollectionUtils.isEmpty(fieldErrors)) {
                    FieldError first = fieldErrors.get(0);
                    msg = first.getDefaultMessage();
                    errorField = first.getField();
                }
            }
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException pe = (MissingServletRequestParameterException) e;

            msg = "缺少参数" + pe.getParameterName();
        }


        Result result = buildBaseResult(request, CoreErrorConst.SYS_INVALID_REQUEST, msg,
                ErrorTypeEnum.APPLICATION.getCode(), getMessage(e));
        //result.setErrorField(errorField);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(value = {
            ConversionNotSupportedException.class,
            HttpMessageNotWritableException.class,
            MultipartException.class,
            NoHandlerFoundException.class,
            Exception.class})
    @ResponseBody
    public ResponseEntity<Result> handleException(HttpServletRequest request, Exception e) {
        log.error("Unexpected exceptions!!!", e);

        String errorMsg = null;
        String errorCode = CoreErrorConst.SYS_EXCEPTION;

        if (e instanceof MultipartException) {
            errorMsg = "文件大小不能大于50M";
        }
        if (e instanceof NoHandlerFoundException) {
            errorCode = CoreErrorConst.SYS_RES_NOT_EXIST;
        }

        return new ResponseEntity<>(buildBaseResult(request, errorCode, errorMsg,
                ErrorTypeEnum.APPLICATION.getCode(), getMessage(e)), HttpStatus.OK);
    }

    private Result buildBaseResult(HttpServletRequest request,
                                   String errorCode,
                                   String errorMsg,
                                   String errorType,
                                   String errorMsgForLog) {
        printHttpHeader(request);

        // 返回给前端的结果
        Result result = getBaseResult(errorCode, errorMsg, errorType);

        // 如果是开发环境则把异常抛出去


        return result;
    }

    protected Result getBaseResult(String errorCode, String errorMsg, String errorType) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(errorCode);
//        result.setErrorType(errorType);
        result.setMsg(getErrorMessage(errorCode, errorMsg));
//        result.setRequestId(MDC.get(CoreErrorConst.MDC_REQ_ID));

        try {
            if (CoreConst.HAS_SOFA_TRACER) {
                String traceId = SofaTraceContextHolder.getSofaTraceContext()
                                                       .getCurrentSpan()
                                                       .getSofaTracerSpanContext()
                                                       .getTraceId();
                result.setTraceId(traceId);
            }
        } catch (Exception e) {

        }
        log.error("errorCode={},errorMessage={}", result.getCode(), result.getMsg());

        return result;
    }

    /**
     * 如果Exception Message为空，则获取异常的堆栈信息
     *
     * @param exception
     * @return
     */
    protected String getMessage(Exception exception) {
        String message = exception.getMessage();
        if (!Strings.isNullOrEmpty(message)) {
            return message;
        }
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            exception.printStackTrace(new PrintWriter(buffer, true));
            message = buffer.toString();
        } catch (Exception inner) {

        }

        return message;
    }

    protected String getErrorMessage(String errorCode, String errorMessage) {
        String desc = errorMessage;
        if (Strings.isNullOrEmpty(errorMessage)) {
            desc = messageSource.getMessage(errorCode, null, errorCode, Locale.CHINESE);
        }
        return desc;
    }

    /**
     * 打印 http header info
     *
     * @param request
     */
    protected void printHttpHeader(HttpServletRequest request) {
//        Long userId = UserContext.getLoginUserIdIfNeed();
//
//        String token = request.getHeader(HttpHeaderDef.TOKEN);
//        String deviceId = request.getHeader(HttpHeaderDef.DEVICE_ID);
//        String osType = request.getHeader(HttpHeaderDef.CLIENT_TYPE);
//        String clientVersion = request.getHeader(HttpHeaderDef.SOFT_VERSION);
//        String userAgent = request.getHeader(HttpHeaderDef.USER_AGENT);
//
//        log.info("userId={},token={},deviceId={},osType={},clientVersion={}",
//                userId, token, deviceId, osType, clientVersion);
//        log.info("ip={},User Agent={}", IPUtil.getIpAddr(request), userAgent);
    }


}
