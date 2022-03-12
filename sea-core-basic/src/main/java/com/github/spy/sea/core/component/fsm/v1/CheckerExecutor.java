package com.github.spy.sea.core.component.fsm.v1;

import com.github.spy.sea.core.model.Result;
import com.github.spy.sea.core.thread.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
     * @param <T>
     * @return
     */
    public <T> Result<T> serialCheck(FsmContext context, List<Checker> checkers) {

        if (CollectionUtils.isEmpty(checkers)) {
            return Result.success();
        }

        Collections.sort(checkers, ORDER_BY_ORDER_ASC);

        for (int i = 0; i < checkers.size(); i++) {
            Checker checker = checkers.get(i);
            Result checkResult = checker.check(context);

            if (checkResult.isFail()) {
                return checkResult;
            }
        }
        return Result.success();
    }

    /**
     * 执行 并行校验器， 按照任务投递的顺序判断返回。
     *
     * @param context  ctx
     * @param checkers checker
     * @param <T>
     * @return
     */
    public <T> Result<T> parallelCheck(FsmContext context, List<Checker> checkers) {
        if (CollectionUtils.isEmpty(checkers)) {
            return Result.success();
        }

        if (checkers.size() == 1) {
            return checkers.get(0).check(context);
        }
        List<Future<Result>> resultList = Collections.synchronizedList(new ArrayList<>(checkers.size()));
        checkers.sort(Comparator.comparingInt(Checker::order));
        for (Checker c : checkers) {
            Future<Result> future = executor.submit(() -> c.check(context));
            resultList.add(future);
        }
        for (Future<Result> future : resultList) {
            try {
                Result sr = future.get();
                if (!sr.isOk()) {
                    return sr;
                }
            } catch (Exception e) {
                log.error("parallelCheck executor.submit error.", e);
                throw new RuntimeException(e);
            }
        }

        return Result.success();
    }
}
