package com.github.seaxlab.core.spring.tx.util;

import com.github.seaxlab.core.exception.Precondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * tx util
 * <p>
 * it has the same behavior with @Transactional
 * </p>
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
   * return whether has active transaction
   *
   * @return
   */
  public static boolean hasActive() {
    return TransactionSynchronizationManager.isActualTransactionActive();
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
    return begin(transactionManager, DefaultTransactionDefinition.PROPAGATION_REQUIRED);
  }

  /**
   * begin new trans
   *
   * @param transactionManager
   * @return
   */
  public static TransactionStatus beginNew(DataSourceTransactionManager transactionManager) {
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
   * begin trans
   *
   * @param transactionManager
   * @param txDefinition
   * @return
   */
  public static TransactionStatus begin(DataSourceTransactionManager transactionManager, TransactionDefinition txDefinition) {
    Precondition.checkNotNull(txDefinition, "transaction definition cannot be null.");

    return transactionManager.getTransaction(txDefinition);
  }

  /**
   * tx manager commit tx status.
   *
   * @param transactionManager txManager
   * @param transStatus        tx status
   */
  public static void commit(DataSourceTransactionManager transactionManager, TransactionStatus transStatus) {
    if (transStatus == null) {
      log.warn("transaction status is null, so skip.");
      return;
    }
    transactionManager.commit(transStatus);
  }

  /**
   * tx manager rollback tx status.
   *
   * @param transactionManager txManager
   * @param transStatus        tx status.
   */
  public static void rollback(DataSourceTransactionManager transactionManager, TransactionStatus transStatus) {
    if (transStatus == null) {
      log.warn("transaction status is null, so skip.");
      return;
    }
    transactionManager.rollback(transStatus);
  }


  /**
   * execute after tx commit
   *
   * @param callback call back function
   */
  public static void registerAfterCommit(Callback callback) {
    log.info("register after commit");
    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
      public void afterCommit() {
        log.info("tx after commit");
        callback.execute();
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

  public interface Callback {
    void execute();
  }

}
