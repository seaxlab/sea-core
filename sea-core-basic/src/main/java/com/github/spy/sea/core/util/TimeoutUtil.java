package com.github.spy.sea.core.util;

import com.github.spy.sea.core.exception.Precondition;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/15
 * @since 1.0
 */
@Slf4j
public final class TimeoutUtil {

    public static Timer check(long timeout, TimeUnit timeUnit, Runnable runnable) {
        Precondition.checkState(timeout > 0, "timeout必须大于0");
        Precondition.checkNotNull(timeUnit, "timeUnit cannot be null");
        Precondition.checkNotNull(runnable, "runnable cannot be null");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task, TimeUnit.MILLISECONDS.convert(timeout, timeUnit));
        return timer;
    }


}
