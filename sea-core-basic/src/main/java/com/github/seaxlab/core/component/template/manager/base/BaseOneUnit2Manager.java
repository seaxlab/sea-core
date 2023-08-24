package com.github.seaxlab.core.component.template.manager.base;

import com.github.seaxlab.core.component.template.checker.Checker;
import com.github.seaxlab.core.component.template.manager.OneUnit2Manager;
import com.github.seaxlab.core.thread.util.ThreadContextUtil;
import com.google.common.base.Stopwatch;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * base one unit2 manager
 *
 * @author spy
 * @version 1.0 2023/08/24
 * @since 1.0
 */
@Slf4j
public abstract class BaseOneUnit2Manager<I, R> implements OneUnit2Manager<I, R> {

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R execute(I bo) {
    logRequest(bo, "");
    //
    return handle0(bo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
  public R executeInNestedTx(I bo) {
    logRequest(bo, " in nested tx");
    //
    return handle0(bo);
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

  public void before(I bo) {
  }

  public abstract R handle(I bo);

  public void afterHandle(I bo, R resp) {

  }

  public void complete(I bo, R resp) {

  }

  //------------------------ private --------------------------
  private R handle0(I bo) {
    R resp = null;
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      Checker<I> checker = getChecker();
      if (Objects.nonNull(checker)) {
        checker.check(bo);
      }
      //
      before(bo);
      //
      resp = handle(bo);
      afterHandle(bo, resp);
    } finally {
      try {
        complete(bo, resp);
      } finally {
        stopwatch.stop();
        //
        if (getBidFlag()) {
          log.info("{} end, cost={}ms", getBizName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
        } else {
          log.info("{},{} end, cost={}ms", ThreadContextUtil.getRequestNo(), getBizName(),
            stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
      }
    }

    return resp;
  }

  private void logRequest(I bo, String beginEnd) {
    if (getBidFlag()) {
      if (getLogRequestBoFlag()) {
        log.info("{},{} begin{},bo={}", ThreadContextUtil.getRequestNo(), getBizName(), beginEnd, bo);
      } else {
        log.info("{},{} begin{}", ThreadContextUtil.getRequestNo(), getBizName(), beginEnd);
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
