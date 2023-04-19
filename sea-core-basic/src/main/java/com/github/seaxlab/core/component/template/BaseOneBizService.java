package com.github.seaxlab.core.component.template;

import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * base one biz service
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
@Slf4j
public abstract class BaseOneBizService<I> implements OneBizService<I> {

  private ApplicationContext context;


  @Autowired
  public void setContext(ApplicationContext context) {
    this.context = context;
  }

  @Override
  public void execute(I bo) {
    Checker<I> checker = getChecker();
    if (Objects.nonNull(checker)) {
      checker.check(bo);
    }

    String lockKey = getLockKey();
    if (StringUtil.isNotBlank(lockKey)) {
      LockService lockService = context.getBean(LockService.class);
      lockService.tryLock(lockKey, getBizName(), () -> action(bo));
    } else {
      action(bo);
    }
  }

  public abstract String getBizName();

  public Checker<I> getChecker() {
    return null;
  }

  public String getLockKey() {
    return StringUtil.empty();
  }

  public void throwLockFailException() {
    ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
  }

  public abstract void action(I bo);
}
