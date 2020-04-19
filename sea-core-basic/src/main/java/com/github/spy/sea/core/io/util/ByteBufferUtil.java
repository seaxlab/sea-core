package com.github.spy.sea.core.io.util;

import lombok.extern.slf4j.Slf4j;

/**
 * ByteBuffer util
 *
 * @author spy
 * @version 1.0 2019-08-08
 * @since 1.0
 */
@Slf4j
public final class ByteBufferUtil {

    private ByteBufferUtil() {
    }

    /**
     * 字节数组转化成16进制形式
     */
    public static String bytes2string(byte[] src) {
        StringBuilder sb = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * 16进制字符串转化成字节数组
     */
    public static byte[] string2bytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return new byte[0];
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }


    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
