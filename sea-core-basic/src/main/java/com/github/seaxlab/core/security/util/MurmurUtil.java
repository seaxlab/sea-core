package com.github.seaxlab.core.security.util;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * hash值，如果分成桶，数据分布是很均匀的，可以参考MurmurUtilTest
 * <p>
 * MD5 是加密型哈希函数，主要关注加解密的安全性，所以会牺牲很多性能。
 * 而 MurmurHash 是一种非加密型哈希函数，适用于一般的哈希检索操作。
 * 与其它流行的哈希函数相比，对于长度较长的 key，MurmurHash 的随机分布特征表现更良好，
 * 发生 Hash 碰撞的几率更低。比起 MD5，它的性能至少提升了一个数量级。
 * </p>
 * <p>
 * MurmurHash 的最新版本是 MurmurHash3，提供了 32 bit, 128 bit 这两种长度的哈希值。
 * URL 经过 MurmurHash 计算后会生成一个整数。32bit 可以表示的数值范围为 0 ~ 4294967295, 约 43 亿，
 * 而基于我们前面的估计，系统 5 年内生成的 URL 数量约 18 亿，所以选择 32bit 的哈希值就足以满足需求了
 * </p>
 *
 * <p>
 * 返回值是个纯数字，有正负之分
 * </p>
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
    public static int hash3_32(String data) {
        return Hashing.murmur3_32().hashString(data, StandardCharsets.UTF_8).hashCode();
    }

    /**
     * hash值，有正\负\0
     *
     * @param data 待加密数据
     * @return
     */
    public static int hash3_128(String data) {
        return Hashing.murmur3_128().hashString(data, StandardCharsets.UTF_8).hashCode();
    }

    /**
     * hash值，只有非负数
     *
     * @param data 待加密数据
     * @return
     */
    public static int hash3_32_positive(String data) {
        return Math.abs(hash3_32(data));
    }

    /**
     * hash值，只有非负数
     *
     * @param data 待加密数据
     * @return
     */
    public static int hash3_128_positive(String data) {
        return Math.abs(hash3_128(data));
    }


}
