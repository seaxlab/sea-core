package com.github.seaxlab.core.model.util;

import com.github.seaxlab.core.enums.IBaseEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/13
 * @since 1.0
 */
@Slf4j
public class BaseEnumUtil {

    /**
     * get code
     *
     * @param typeEnum
     * @return
     */
    public static final <T> T getCode(IBaseEnum<T> typeEnum) {
        if (typeEnum == null) {
            return null;
        }
        return typeEnum.getCode();
    }

    /**
     * get desc
     *
     * @param typeEnum
     * @return
     */
    public static final <T> String getDesc(IBaseEnum<T> typeEnum) {
        if (typeEnum == null) {
            return null;
        }
        return typeEnum.getDesc();
    }

}
