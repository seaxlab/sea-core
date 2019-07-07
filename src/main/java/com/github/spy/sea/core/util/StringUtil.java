package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;

import java.util.Objects;
import java.util.Set;

/**
 * String 工具
 *
 * @author spy
 */
public class StringUtil {

    /**
     * 字符串是否为空
     *
     * @param params
     * @return
     */
    public static boolean isAllEmpty(Object... params) {

        for (Object param : params) {
            if (param != null && param.toString().length() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串是否为空
     *
     * @param params
     * @return
     */
    public static boolean isEmpty(Object... params) {

        for (Object param : params) {
            if (param == null || "".equals(param)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串是否不为空
     *
     * @param params
     * @return
     */
    public static boolean isNotEmpty(Object... params) {

        for (Object param : params) {
            if (param == null || "".equals(param)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串左侧补零
     *
     * @param obj
     * @param strLength
     * @return
     */
    public static String addZeroLeft(Object obj, int strLength) {
        String str = obj == null ? "" : obj.toString();
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                // 左补0
                sb.append("0").append(str);
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 将set 集合转成 String
     *
     * @param set
     * @return
     */
    public static String setToString(Set set) {
        if (null == set || set.size() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Object object : set) {
            if (object instanceof String || object instanceof Long || object instanceof Integer) {
                if (isNotEmpty(object.toString())) {
                    stringBuffer.append(object.toString()).append(",");
                }
            }
        }
        if (stringBuffer.length() == 0) {
            return "";
        }
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }

    /**
     * 判断是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean eq(String str1, String str2) {
        if (Objects.nonNull(str1) && Objects.nonNull(str2)) {
            return str1.equals(str2);
        }
        return false;
    }

    /**
     * 设置返回有值的参数
     *
     * @param str1
     * @param args
     * @return
     */
    public static String defaultIfBlank(String str1, String... args) {
        Preconditions.checkNotNull(args, "参数不能为空");
        if (isEmpty(str1)) {
            for (int i = 0; i < args.length; i++) {
                String item = args[i];
                if (isEmpty(item)) {
                    continue;
                } else {
                    return item;
                }
            }
        } else {
            return str1;
        }

        return null;
    }

}
