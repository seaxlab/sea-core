package com.github.spy.sea.core.security.util;

import com.github.spy.sea.core.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class Base64Util {

    private static final String DEFAULT_CHARSET = "utf-8";

    private Base64Util() {
    }

    /**
     * 编码
     *
     * @param value
     * @return
     */
    public static String encode(String value) {
        try {
            return Base64.encodeBase64String(value.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {

            log.error("base64 encode", e);
            ExceptionHandler.publishMsg("base64 encode 失败.");
        }
        return null;
    }

    /**
     * 解码
     *
     * @param base64Str
     * @return
     */
    public static String decode(String base64Str) {


        try {
            return new String(Base64.decodeBase64(base64Str), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error("base64 decode", e);
            ExceptionHandler.publishMsg("base64 decode 失败.");
        }
        return null;
    }

}
