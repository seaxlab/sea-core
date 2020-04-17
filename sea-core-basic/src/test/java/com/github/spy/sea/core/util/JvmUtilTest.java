package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.config.Configuration;
import com.github.spy.sea.core.config.ConfigurationFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */
@Slf4j
public class JvmUtilTest extends BaseCoreTest {

    @Test
    public void getPidTest() throws Exception {
        log.info(JvmUtil.getPID());
    }

    @Test
    public void run30() throws Exception {
        JvmUtil.dumpStack();
    }

    @Test
    public void dumpStackTest() throws Exception {
        Configuration cfg = ConfigurationFactory.getInstance();

        cfg.getString("user.home");

        JvmUtil.dumpStack(getUserHome() + "/logs/jsdump.log");

        TimeUnit.MINUTES.sleep(2);
    }

    @Test
    public void run38() throws Exception {

    log.info("{}",crunchifyGenerateThreadDump());
    TimeUnit.MINUTES.sleep(1);
    }

    public static String crunchifyGenerateThreadDump() {
        final StringBuilder dump = new StringBuilder();
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 100);
        for (ThreadInfo threadInfo : threadInfos) {
            dump.append('"');
            dump.append(threadInfo.getThreadName());
            dump.append("\" ");
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
