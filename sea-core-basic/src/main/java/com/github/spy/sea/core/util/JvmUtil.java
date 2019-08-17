package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.lang.management.ManagementFactory;

/**
 * jvm util
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */
@Slf4j
public final class JvmUtil {

    public static String P_ID_CACHE = null;

    /**
     * 此方法在 JDK9 下可以有更加好的方式，但是目前的几个 JDK 版本下，只能通过这个方式来搞。
     * 在 Mac 环境下，JDK6，JDK7，JDK8 都可以跑过。
     * 在 Linux 环境下，JDK6，JDK7，JDK8 尝试过，可以运行通过。
     *
     * @return 进程 ID
     */
    public static String getPID() {
        //check pid is cached
        if (P_ID_CACHE != null) {
            return P_ID_CACHE;
        }
        String processName = ManagementFactory.getRuntimeMXBean().getName();

        if (StringUtils.isBlank(processName)) {
            return StringUtils.EMPTY;
        }

        String[] processSplitName = processName.split("@");

        if (processSplitName.length == 0) {
            return StringUtils.EMPTY;
        }

        String pid = processSplitName[0];

        if (StringUtils.isBlank(pid)) {
            return StringUtils.EMPTY;
        }
        P_ID_CACHE = pid;
        return pid;
    }

    // jdk9
//    public static String getPID(){
//        long pid = ProcessHandle.current().pid();
//    }
}
