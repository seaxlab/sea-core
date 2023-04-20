package com.github.seaxlab.core.component.template.service;

import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.template.checker.Checker;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.util.StringUtil;
import java.util.Objects;
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
public abstract class BaseOneBiz2Service implements OneBiz2Service {

  private ApplicationContext context;


  @Autowired
  public void setContext(ApplicationContext context) {
    this.context = context;
  }

  @Override
  public <I, R> R execute(I bo) {
    Checker checker = getChecker();
    if (Objects.nonNull(checker)) {
      checker.check(bo);
    }

    R response;

    String lockKey = getLockKey();
    if (StringUtil.isNotBlank(lockKey)) {
      LockService lockService = context.getBean(LockService.class);
      //
      response = lockService.tryLock(lockKey, getBizName(), () -> action(bo));
    } else {
      response = action(bo);
    }

    return response;
  }

  public abstract String getBizName();

  public Checker getChecker() {
    return null;
  }

  public String getLockKey() {
    return StringUtil.empty();
  }

  public void throwLockFailException() {
    ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
  }

  public abstract <I, R> R action(I bo);
}
