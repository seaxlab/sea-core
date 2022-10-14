package com.github.seaxlab.core.dal.util;

import com.github.seaxlab.core.enums.IErrorEnum;
import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

/**
 * dal util for check row count
 *
 * @author spy
 * @version 1.0 2022/10/14
 * @since 1.0
 */
@Slf4j
public final class DalUtil {

    private DalUtil() {
    }

    /**
     * check db effect row count
     *
     * @param rowCount
     * @param remark
     */
    public static void check(int rowCount, String remark) {
        log.info("row count={}, {}", rowCount, remark);
        if (rowCount <= 0) {
            throw new BaseAppException(ErrorMessageEnum.DB_OPERATION_FAIL);
        }
    }

    /**
     * check db effect row count
     *
     * @param rowCount
     * @param remark
     * @param iErrorEnum
     */
    public static void check(int rowCount, String remark, IErrorEnum iErrorEnum) {
        log.info("row count={}, {}", rowCount, remark);
        if (rowCount <= 0) {
            throw new BaseAppException(iErrorEnum);
        }
    }

    /**
     * check db effect row count
     *
     * @param rowCount
     * @param remark
     * @param args
     */
    public static void checkF(int rowCount, String remark, Object... args) {
        String message = MessageFormatter.arrayFormat(remark, args).getMessage();
        log.info("row count={}, {}", rowCount, message);
        if (rowCount <= 0) {
            throw new BaseAppException(ErrorMessageEnum.DB_OPERATION_FAIL);
        }
    }

    /**
     * check db effect row count
     *
     * @param flag
     * @param remark
     */
    public static void check(boolean flag, String remark) {
        log.info("check db flag={}, {}", flag, remark);
        if (!flag) {
            throw new BaseAppException(ErrorMessageEnum.DB_OPERATION_FAIL);
        }
    }

    /**
     * check db effect row count
     *
     * @param flag
     * @param remark
     * @param iErrorEnum
     */
    public static void check(boolean flag, String remark, IErrorEnum iErrorEnum) {
        log.info("check db flag={}, {}", flag, remark);
        if (!flag) {
            throw new BaseAppException(iErrorEnum);
        }
    }

}
