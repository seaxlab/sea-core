package com.github.seaxlab.core.lang.jvm.manager;

import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.lang.management.*;
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
  private static final String LOGO_TITLE = "dump create by sea-core, design by SPY.";

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
    dump.append(DateUtil.toString(new Date(), DateFormatEnum.DEFAULT)).append(" ").append(LOGO_TITLE).append("\n\n");

    final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    threadMXBean.setThreadContentionMonitoringEnabled(true);

    threadMXBean.dumpAllThreads(true, true);
    final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 100);

    for (ThreadInfo threadInfo : threadInfos) {
//            log.debug("threadInfo={}", threadInfo);
      dump.append('"').append(threadInfo.getThreadName()).append("\" ").append(" tid=").append(threadInfo.getThreadId());


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

  /**
   * dump use output stream.
   * implement from apache dubbo.
   *
   * @param stream
   * @throws Exception
   */
  public static void dump(OutputStream stream) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append(DateUtil.toString(new Date(), DateFormatEnum.DEFAULT)).append(" ").append(LOGO_TITLE).append("\n\n");
    stream.write(sb.toString().getBytes());

    ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
    for (ThreadInfo threadInfo : threadMxBean.dumpAllThreads(true, true)) {
      stream.write(getThreadDumpString(threadInfo).getBytes());
    }
  }

  /**
   * dump java stack.
   *
   * @return
   */
  public static String dumpStandard() {
    StringBuilder sb = new StringBuilder();
    sb.append(DateUtil.toString(new Date(), DateFormatEnum.DEFAULT)).append(" ").append(LOGO_TITLE).append("\n\n");


    ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
    for (ThreadInfo threadInfo : threadMxBean.dumpAllThreads(true, true)) {
      sb.append(getThreadDumpString(threadInfo));
    }
    return sb.toString();
  }


  private static String getThreadDumpString(ThreadInfo threadInfo) {
    StringBuilder sb = new StringBuilder("\"" + threadInfo.getThreadName() + "\"" + " Id=" + threadInfo.getThreadId() + " " + threadInfo.getThreadState());
    if (threadInfo.getLockName() != null) {
      sb.append(" on " + threadInfo.getLockName());
    }
    if (threadInfo.getLockOwnerName() != null) {
      sb.append(" owned by \"" + threadInfo.getLockOwnerName() + "\" Id=" + threadInfo.getLockOwnerId());
    }
    if (threadInfo.isSuspended()) {
      sb.append(" (suspended)");
    }
    if (threadInfo.isInNative()) {
      sb.append(" (in native)");
    }
    sb.append('\n');
    int i = 0;

    StackTraceElement[] stackTrace = threadInfo.getStackTrace();
    MonitorInfo[] lockedMonitors = threadInfo.getLockedMonitors();
    for (; i < stackTrace.length && i < 32; i++) {
      StackTraceElement ste = stackTrace[i];
      sb.append("\tat " + ste.toString());
      sb.append('\n');
      if (i == 0 && threadInfo.getLockInfo() != null) {
        Thread.State ts = threadInfo.getThreadState();
        switch (ts) {
          case BLOCKED:
            sb.append("\t-  blocked on " + threadInfo.getLockInfo());
            sb.append('\n');
            break;
          case WAITING:
            sb.append("\t-  waiting on " + threadInfo.getLockInfo());
            sb.append('\n');
            break;
          case TIMED_WAITING:
            sb.append("\t-  waiting on " + threadInfo.getLockInfo());
            sb.append('\n');
            break;
          default:
        }
      }

      for (MonitorInfo mi : lockedMonitors) {
        if (mi.getLockedStackDepth() == i) {
          sb.append("\t-  locked " + mi);
          sb.append('\n');
        }
      }
    }
    if (i < stackTrace.length) {
      sb.append("\t...");
      sb.append('\n');
    }

    LockInfo[] locks = threadInfo.getLockedSynchronizers();
    if (locks.length > 0) {
      sb.append("\n\tNumber of locked synchronizers = " + locks.length);
      sb.append('\n');
      for (LockInfo li : locks) {
        sb.append("\t- " + li);
        sb.append('\n');
      }
    }
    sb.append('\n');
    return sb.toString();
  }

}
