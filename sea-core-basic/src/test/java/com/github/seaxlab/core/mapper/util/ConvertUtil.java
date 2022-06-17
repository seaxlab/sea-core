package com.github.seaxlab.core.mapper.util;

import com.github.seaxlab.core.util.RegExpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/15
 * @since 1.0
 */
@Slf4j
public class ConvertUtil {

    public static Integer toInt(String str) {
        if (str == null) {
            return null;
        }

        if (RegExpUtil.isNumeric(str)) {
            return Integer.valueOf(str);
        }
        return null;
    }
}
