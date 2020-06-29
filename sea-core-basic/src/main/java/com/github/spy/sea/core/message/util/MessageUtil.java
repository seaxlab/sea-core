package com.github.spy.sea.core.message.util;

import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

/**
 * 模块名
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
     * 格式化信息
     *
     * @param messagePattern
     * @param args
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

}