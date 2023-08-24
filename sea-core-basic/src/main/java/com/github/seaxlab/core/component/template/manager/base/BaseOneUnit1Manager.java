package com.github.seaxlab.core.component.template.manager.base;

import com.github.seaxlab.core.component.template.checker.Checker;
import com.github.seaxlab.core.component.template.manager.OneUnit1Manager;
import com.github.seaxlab.core.thread.util.ThreadContextUtil;
import com.google.common.base.Stopwatch;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * base one unit1 manager
 *
 * @author spy
 * @version 1.0 2023/08/24
 * @since 1.0
 */
@Slf4j
public abstract class BaseOneUnit1Manager<I> implements OneUnit1Manager<I> {

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void execute(I bo) {
    logRequest(bo, "");
    //
    handle0(bo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public void executeInNestedTx(I bo) {
    logRequest(bo, " in nested tx");
    //
    handle0(bo);
  }

  /**
   * 业务名
   *
   * @return
   */
  public abstract String getBizName();

  /**
   * 输出bid
   *
   * @return
   */
  public boolean getBidFlag() {
    return false;
  }

  public boolean getLogRequestBoFlag() {
    return false;
  }

  public Checker<I> getChecker() {
    return null;
  }

  public void beforeHandle(I bo) {
  }

  public abstract void handle(I bo);

  public void afterHandle(I bo) {

  }

  public void complete(I bo) {

  }

  //------------------------ private --------------------------
  private void handle0(I bo) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      Checker<I> checker = getChecker();
      if (Objects.nonNull(checker)) {
        checker.check(bo);
      }
      //
      beforeHandle(bo);
      //
      handle(bo);
      //
      afterHandle(bo);
    } finally {
      try {
        complete(bo);
      } finally {
        stopwatch.stop();
        if (getBidFlag()) {
          log.info("{} end, cost={}ms", getBizName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
        } else {
          log.info("{},{} end, cost={}ms", ThreadContextUtil.bid(), getBizName(),
            stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
      }
    }
  }

  private void logRequest(I bo, String beginEnd) {
    if (getBidFlag()) {
      if (getLogRequestBoFlag()) {
        log.info("{},{} begin{},bo={}", ThreadContextUtil.bid(), getBizName(), beginEnd, bo);
      } else {
        log.info("{},{} begin{}", ThreadContextUtil.bid(), getBizName(), beginEnd);
      }
    } else {
      if (getLogRequestBoFlag()) {
        log.info("{} begin{},bo={}", getBizName(), beginEnd, bo);
      } else {
        log.info("{} begin{}", getBizName(), beginEnd);
      }
    }
  }
}
