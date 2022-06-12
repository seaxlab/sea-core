package com.github.seaxlab.core.security.util;

import cn.hutool.core.codec.Base62;
import lombok.extern.slf4j.Slf4j;

/**
 * 与base64相比去掉了+/-=符号，0-9,A-Z,a-z
 * <p>
 * 注意这里有几种组合方式（0-9,A-Z,a-z 或 0-9,a-z,A-Z）
 * </p>
 *
 * @author spy
 * @version 1.0 2021/5/4
 * @since 1.0
 */
@Slf4j
public class Base62Util {

    public static String encode(String val) {
        return Base62.encode(val);
    }

    public static String decode(String val) {
        return Base62.decodeStr(val);
    }

}
