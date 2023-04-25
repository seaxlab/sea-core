package com.github.seaxlab.core.component.template.service;

import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.lock.request.LockConfig;
import com.github.seaxlab.core.component.template.checker.Checker;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.SetUtil;
import com.github.seaxlab.core.util.StringUtil;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

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
    handle();
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

  public void init() {
  }


  public abstract void handle();
}
