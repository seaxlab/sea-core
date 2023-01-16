package com.github.seaxlab.core.spring.service;

import com.github.seaxlab.core.component.lock.model.DistributeLockKey;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.model.checker.BaseSimpleChecker;
import com.github.seaxlab.core.spring.context.SpringContextHolder;
import com.github.seaxlab.core.util.StringUtil;
import java.util.concurrent.locks.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * abstract service
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public abstract class AbstractOneBizService<I, R> {

  /**
   * execute main business logic
   *
   * @param input business object
   * @return business process result.
   */
  public R execute(I input) {
    R data;
    try {
      check(input);

      String lockKey = getLockKey();
      if (StringUtil.isNotBlank(lockKey)) {
        DistributeLockKey lockBean = SpringContextHolder.getBean(DistributeLockKey.class);
        Lock lock = lockBean.get(lockKey);
        boolean lockFlag = lock.tryLock();
        log.info("{} lock flag={}, key={}", getScene(), lockFlag, lockKey);

        if (!lockFlag) {
          // how to integrate with business system ?
          ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
        }
        try {
          data = handleAll(input);
        } finally {
          unlock(lock);
        }
      } else {
        data = handleAll(input);
      }

    } finally {
      destroy();
    }

    return data;
  }

  protected void check(I input) {
    // bean name --> bean class
    //
    if (StringUtil.isNotBlank(getCheckBean())) {
      BaseSimpleChecker<I> checker = SpringContextHolder.getBean(getCheckBean());
      log.info("{} check begin", getScene());
      checker.check(input);
      log.info("{} check end", getScene());
      return;
    }
    //
    if (getCheckClass() != null) {
      BaseSimpleChecker checker = SpringContextHolder.getBean(getCheckClass());
      log.info("{} check begin", getScene());
      checker.check(input);
      log.info("{} check end", getScene());
    }

  }

  protected String getLockKey() {
    return "";
  }

  public R handleAll(I input) {
    beforeHandle(input);
    //
    if (getTransactionFlag()) {
      TransactionTemplate transactionTemplate = SpringContextHolder.getBean(TransactionTemplate.class);
      return transactionTemplate.execute(status -> handle(input));
    } else {
      return handle(input);
    }
  }

  protected void beforeHandle(I input) {
  }

  abstract R handle(I input);

  protected void destroy() {
  }

  /**
   * 业务场景名称
   *
   * @return
   */
  abstract String getScene();

  //------------------------------- checker begin -----------------------------
  protected String getCheckBean() {
    return "";
  }

  protected Class<? extends BaseSimpleChecker> getCheckClass() {
    return null;
  }
  //------------------------------- checker end ---------------------------------

  protected boolean getTransactionFlag() {
    return true;
  }

  //--------------------------------private
  private void unlock(Lock lock) {
    if (lock == null) {
      return;
    }
    try {
      lock.unlock();
    } catch (Exception e) {
      log.error("fail to unlock", e);
    }
  }
}
