package com.github.spy.sea.core.jvm.manager;

import com.github.spy.sea.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/17
 * @since 1.0
 */
@Slf4j
public class StackManager {
    private StackManager() {
    }
    // jstack thread format
//            "Attach Listener" #128 daemon prio=9 os_prio=0 tid=0x00007f7adc001000 nid=0x7578 waiting on condition [0x0000000000000000]
//            java.lang.Thread.State: RUNNABLE
//
//            "scheduler-6" #127 prio=5 os_prio=0 tid=0x00007f7a20008800 nid=0x750f waiting on condition [0x00007f79967e6000]
//            java.lang.Thread.State: WAITING (parking)
//            at sun.misc.Unsafe.park(Native Method)
//                    - parking to wait for  <0x00000000aebe7578> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)

    /**
     * dump stack to string
     *
     * @return string
     */
    public static String dump() {
        final StringBuilder dump = new StringBuilder();
        dump.append(DateUtil.toYMDHMS(new Date())).append(" dump create by sea-core\n\n");

        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        threadMXBean.setThreadContentionMonitoringEnabled(true);

        final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 100);

        for (ThreadInfo threadInfo : threadInfos) {
//            log.debug("threadInfo={}", threadInfo);
            dump.append('"')
                .append(threadInfo.getThreadName())
                .append("\" ")
                .append(" tid=").append(threadInfo.getThreadId());


            final Thread.State state = threadInfo.getThreadState();
            dump.append("\n   java.lang.Thread.State: ");
            dump.append(state);
            final StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
            for (final StackTraceElement stackTraceElement : stackTraceElements) {
                dump.append("\n        at ");
                dump.append(stackTraceElement);
            }
            dump.append("\n\n");
        }

        return dump.toString();
    }

}
