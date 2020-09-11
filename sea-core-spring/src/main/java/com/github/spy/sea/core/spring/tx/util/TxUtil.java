package com.github.spy.sea.core.spring.tx.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * tx util
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */
@Slf4j
public final class TxUtil {

    private TxUtil() {
    }

    /**
     * 回滚事务
     */
    public static void rollback() {
        log.info("set tx rollback only.");
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    /**
     * begin new trans
     *
     * @param transactionManager
     * @return
     */
    public static TransactionStatus begin(DataSourceTransactionManager transactionManager) {
        return begin(transactionManager, DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    /**
     * begin new trans.
     *
     * @param transactionManager
     * @param propagationBehavior
     * @return
     */
    public static TransactionStatus begin(DataSourceTransactionManager transactionManager, int propagationBehavior) {

        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
        transDefinition.setPropagationBehavior(propagationBehavior);
        TransactionStatus transStatus = transactionManager.getTransaction(transDefinition);

        return transStatus;
    }

    /**
     * tx manager commit tx status.
     *
     * @param transactionManager txManager
     * @param transStatus        tx status
     */
    public static void commit(DataSourceTransactionManager transactionManager, TransactionStatus transStatus) {
        transactionManager.commit(transStatus);
    }

    /**
     * tx manager rollback tx status.
     *
     * @param transactionManager txManager
     * @param transStatus        tx status.
     */
    public static void rollback(DataSourceTransactionManager transactionManager, TransactionStatus transStatus) {
        transactionManager.rollback(transStatus);
    }
}
