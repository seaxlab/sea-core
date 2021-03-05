package com.github.spy.sea.core.security.util;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/5
 * @since 1.0
 */
@Slf4j
public class Crc32Util {

    /**
     * hash crc32
     *
     * @param data
     * @return
     */
    public static final int hash(String data) {
        return Hashing.crc32().hashString(data, StandardCharsets.UTF_8).hashCode();
    }

}
