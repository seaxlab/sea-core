package com.github.spy.sea.core.spring.tx.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.function.Supplier;

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


    /**
     * 事务提交之后钩子，如需要其他阶段，则使用registerSynchronization
     *
     * @param supplier supplier
     */
    public static void registerAfterCommit(Supplier<Void> supplier) {
        log.info("register after commit");
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            public void afterCommit() {
                log.info("tx after commit");
                supplier.get();
            }
        });
    }

    /**
     * transaction synchronization callbacks
     *
     * @param transactionSynchronization tx sync fun
     */
    public static void registerSynchronization(TransactionSynchronization transactionSynchronization) {
        log.info("register synchronization");
        TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);
    }

}
