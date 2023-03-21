package com.github.seaxlab.core.component.fsm.v1;

import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.thread.util.ThreadPoolUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/27
 * @since 1.0
 */
@Slf4j
public class CheckerExecutor {

  //TODO
  ExecutorService executor = ThreadPoolUtil.get("sea-fsm-pool");


  private static final Comparator<Checker> ORDER_BY_ORDER_ASC = (o1, o2) -> Integer.compare(o1.order(), o2.order());

  /**
   * 执行 串行校验器
   *
   * @param context
   * @param checkers
   * @return
   */
  public void serialCheck(FsmContext context, List<Checker> checkers) {

    if (CollectionUtils.isEmpty(checkers)) {
      return;
    }

    Collections.sort(checkers, ORDER_BY_ORDER_ASC);

    for (int i = 0; i < checkers.size(); i++) {
      Checker checker = checkers.get(i);
      checker.check(context);
    }
  }

  /**
   * 执行 并行校验器， 按照任务投递的顺序判断返回。
   *
   * @param context  ctx
   * @param checkers checker
   * @return
   */
  public void parallelCheck(FsmContext context, List<Checker> checkers) {
    if (CollectionUtils.isEmpty(checkers)) {
      return;
    }

    if (checkers.size() == 1) {
      checkers.get(0).check(context);
      return;
    }
    List<Future<Boolean>> resultList = Collections.synchronizedList(new ArrayList<>(checkers.size()));
    checkers.sort(Comparator.comparingInt(Checker::order));
    for (Checker c : checkers) {
      Future<Boolean> future = executor.submit(() -> {
        try {
          c.check(context);
        } catch (Exception e) {
          log.warn("fail to execute check", e);
          return false;
        }
        return true;
      });
      resultList.add(future);
    }
    for (Future<Boolean> future : resultList) {
      try {
        Boolean flag = future.get();
        if (!flag) {
          ExceptionHandler.publish(ErrorMessageEnum.SYS_EXCEPTION);
        }
      } catch (InterruptedException | ExecutionException e) {
        log.error("parallelCheck executor.submit error.", e);
        throw new RuntimeException(e);
      }
    }

  }
}
