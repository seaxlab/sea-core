package com.github.spy.sea.core.security.util;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * hash值，如果分成桶，数据分布是很均匀的，可以参考MurmurUtilTest
 *
 * @author spy
 * @version 1.0 2021/3/5
 * @since 1.0
 */
@Slf4j
public final class MurmurUtil {

    /**
     * hash值，有正\负\0
     *
     * @param data 待加密数据
     * @return
     */
    public static final int hash3_32(String data) {
        return Hashing.murmur3_32().hashString(data, StandardCharsets.UTF_8).hashCode();
    }

    /**
     * hash值，有正\负\0
     *
     * @param data 待加密数据
     * @return
     */
    public static final int hash3_128(String data) {
        return Hashing.murmur3_128().hashString(data, StandardCharsets.UTF_8).hashCode();
    }

    /**
     * hash值，只有非负数
     *
     * @param data 待加密数据
     * @return
     */
    public static final int hash3_32_positive(String data) {
        return Math.abs(hash3_32(data));
    }

    /**
     * hash值，只有非负数
     *
     * @param data 待加密数据
     * @return
     */
    public static final int hash3_128_positive(String data) {
        return Math.abs(hash3_128(data));
    }


}
