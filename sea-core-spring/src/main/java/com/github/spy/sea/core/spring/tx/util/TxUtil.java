package com.github.spy.sea.core.spring.tx.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * tx util
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */
@Slf4j
public final class TxUtil {

    /**
     * 回滚事务
     */
    public static void rollback() {
        log.info("set tx rollback only.");
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
