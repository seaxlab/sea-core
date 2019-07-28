package com.github.spy.sea.core.spring.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * 资源文件辅助类（默认取中文）
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */
@Slf4j
public class MessageSourceUtil {

    private MessageSourceUtil() {

    }

    /**
     * 获取资源文件
     *
     * @param messageSource
     * @param errorCode
     * @return
     */
    public static String getErrorMsg(MessageSource messageSource, String errorCode) {
        return getErrorMsg(messageSource, errorCode, null);
    }

    /**
     * 获取资源文件
     *
     * @param messageSource
     * @param errorCode
     * @param errorMessage
     * @return
     */
    public static String getErrorMsg(MessageSource messageSource, String errorCode, String errorMessage) {
        String desc = errorMessage;
        if (Strings.isNullOrEmpty(errorMessage)) {
            desc = messageSource.getMessage(errorCode, null, errorCode, Locale.CHINESE);
        }
        return desc;
    }

}
