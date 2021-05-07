package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/6
 * @since 1.0
 */
@Slf4j
public final class TimerUtil {

    /**
     * 每日指定时间执行
     *
     * @param timerTask
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Timer scheduleEveryDay(TimerTask timerTask, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute, second);

        Timer timer = new Timer();
        Date date = new Date();
        timer.schedule(timerTask, date);
        return timer;
    }

    /**
     * 周期性执行
     *
     * @param timerTask task
     * @param period    单位毫秒
     * @return
     */
    public static Timer scheduleByPeriod(TimerTask timerTask, int period) {
        return periodSchedule(timerTask, period, new Date());
    }

    /**
     * 周期性执行
     *
     * @param timerTask task
     * @param period    单位毫秒
     * @param startDate 上次执行时间
     * @return
     */
    public static Timer periodSchedule(TimerTask timerTask, int period, Date startDate) {
        Timer timer = new Timer();
        timer.schedule(timerTask, startDate, period);
        return timer;
    }
}
