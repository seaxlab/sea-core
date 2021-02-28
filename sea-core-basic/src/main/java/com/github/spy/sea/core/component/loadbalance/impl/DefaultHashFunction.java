package com.github.spy.sea.core.component.loadbalance.impl;

import com.github.spy.sea.core.component.loadbalance.HashFunction;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/26
 * @since 1.0
 */
@Slf4j
public class DefaultHashFunction implements HashFunction {
    @Override
    public long hash(String str) {
        byte[] bytes = md5(str);

        return hash(bytes, 0);
    }

    private long hash(byte[] digest, int number) {
        return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                | (digest[number * 4] & 0xFF))
                & 0xFFFFFFFFL;
    }

    private byte[] md5(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        md5.reset();
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        md5.update(bytes);
        return md5.digest();
    }
}
