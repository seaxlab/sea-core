package com.github.seaxlab.core.component.template.service;

import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.lock.request.LockConfig;
import com.github.seaxlab.core.component.template.checker.Checker;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.SetUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.google.common.base.Stopwatch;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * base one biz service
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
@Slf4j
public abstract class BaseOneBiz0Service implements OneBiz0Service {

  private ApplicationContext context;


  @Autowired
  public void setContext(ApplicationContext context) {
    this.context = context;
  }

  @Override
  public void execute() {
    if (getTxAllFlag()) {
      TransactionTemplate txTemplate = context.getBean(TransactionTemplate.class);
      txTemplate.execute(status -> {
        _execute00();
        return null;
      });
    } else {
      _execute00();
    }
  }


  private void _execute00() {
    init();
    //
    String lockKey = getLockKey();
    if (StringUtil.isNotBlank(lockKey)) {
      LockService lockService = context.getBean(LockService.class);
      //
      LockConfig lockConfig = new LockConfig();
      lockConfig.setLockKey(lockKey);
      lockConfig.setBizName(getBizName());
      lockConfig.setThrowOnFailFlag(throwExceptionWhenLockFail());
      //
      lockService.tryLock(lockConfig, () -> handle());
      return;
    }
    //
    Collection<String> lockKeys = getLockKeys();
    if (CollectionUtil.isNotEmpty(lockKeys)) {
      LockService lockService = context.getBean(LockService.class);
      //
      LockConfig lockConfig = new LockConfig();
      lockConfig.setLockKeys(lockKeys);
      lockConfig.setBizName(getBizName());
      lockConfig.setThrowOnFailFlag(throwExceptionWhenLockFail());
      //
      lockService.tryLock(lockConfig, () -> handle());
      return;
    }
    //
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      log.info("{} begin", getBizName());
      handle();
    } finally {
      stopwatch.stop();
      log.info("{} end, cost={}ms", getBizName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
  }

  public abstract String getBizName();

  public Checker getChecker() {
    return null;
  }

  public String getLockKey() {
    return StringUtil.empty();
  }

  public Collection<String> getLockKeys() {
    return SetUtil.empty();
  }

  public boolean throwExceptionWhenLockFail() {
    return true;
  }

  public boolean getTxAllFlag() {
    return false;
  }

  public void init() {
  }


  public abstract void handle();
}
