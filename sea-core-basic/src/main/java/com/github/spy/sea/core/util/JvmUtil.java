package com.github.spy.sea.core.util;

import com.github.spy.sea.core.config.Configuration;
import com.github.spy.sea.core.config.ConfigurationFactory;
import com.github.spy.sea.core.jvm.manager.StackManager;
import com.google.common.util.concurrent.RateLimiter;
import com.sun.management.HotSpotDiagnosticMXBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.management.MBeanServer;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    final static RateLimiter rateLimiter = RateLimiter.create(1, 1, TimeUnit.MINUTES);

    public static void dumpStackLimiter() {
        if (rateLimiter.tryAcquire()) {
            log.info("get token, so do it");
            dumpStack();
            return;
        }
        log.info("no get token, so do it next time.");
    }

    /**
     * dump java stack
     */
    public static void dumpStack() {
        Configuration cfg = ConfigurationFactory.getInstance();
        String userHome = cfg.getString("user.home");

        String logPath = PathUtil.join(userHome, "logs");
        FileUtil.ensureDir(logPath);
        dumpStack(logPath + "/" + DateUtil.toYMDHMSNoPoint(new Date()) + "_" + getPID() + "_jstack.log");
    }

    /**
     * dump java stack
     * it will block current thread
     */
    public static void dumpStack(String filePath) {
        log.info("dump jvm stack, pid={}", getPID());
        try {
            String content = StackManager.dump();

            // this is cat impl
//            ThreadInfo[] threads = bean.dumpAllThreads(false, false);
//            String content = new ThreadInfoWriter().buildThreadsInfo(threads);

            FileUtil.writeFile(filePath, content);
        } catch (Exception e) {
            log.error("fail to dump jvm stack", e);
        }
    }


    /**
     * dump java heap
     *
     * @param filePath
     * @param live
     * @throws IOException
     */
    public static void dumpHeap(String filePath, boolean live) {
        log.info("dump jvm heap, pid={}", getPID());
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            HotSpotDiagnosticMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(
                    server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
            mxBean.dumpHeap(filePath, live);
        } catch (Exception e) {
            log.error("fail to dump jvm heap", e);
        }
    }


}
