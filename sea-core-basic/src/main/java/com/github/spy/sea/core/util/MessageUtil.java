package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

import java.text.MessageFormat;

/**
 * message format util.
 *
 * @author spy
 * @version 1.0 2019/6/1
 * @since 1.0
 */
@Slf4j
public final class MessageUtil {

    private MessageUtil() {
    }

    /**
     * 使用{}格式化信息
     *
     * @param messagePattern 模板字符串，使用{}作为占位符
     * @param args           参数列表
     * @return
     */
    public static String format(String messagePattern, Object... args) {
        return MessageFormatter.arrayFormat(messagePattern, args).getMessage();
    }

    public static String append(String str, String str2) {
        return append(str, str2, ";");
    }

    public static String append(String str, String str2, String splitChar) {
        if (StringUtil.isEmpty(str)) {
            return str2;
        }
        return new StringBuilder(str).append(splitChar)
                                     .append(str2)
                                     .toString();
    }

    /**
     * 使用索引{N}进行格式化信息
     *
     * @param messagePattern 模板字符串，使用{N}作为占位符,N从0开始
     * @param args           参数列表
     * @return
     */
    public static String formatByIndex(String messagePattern, Object... args) {
        return MessageFormat.format(messagePattern, args);
    }

}