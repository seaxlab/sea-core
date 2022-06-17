package com.github.seaxlab.core.util;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 向指定目录输出日志dump文件
 * ${user.home}/logs/sea/${MODULE_NAME}/${yyyyMMdd_HHmmss}_${PID}_${MODULE_NAME}.log
 *
 * @author spy
 * @version 1.0 2021/3/26
 * @since 1.0
 */
@Slf4j
public final class LogUtil {

    private static final Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    /**
     * dump content to custom dir.
     *
     * @param module  module name
     * @param content content
     */
    public static void dump(String module, String content) {
        if (StringUtil.isEmpty(module)) {
            log.warn("module is empty.", module);
            return;
        }

        if (StringUtil.isEmpty(content)) {
            log.warn("{} content is empty.", module);
            return;
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            FileUtil.writeFile(getLogFileNameInModule(module), content);
        } catch (Exception e) {
            log.error("fail to dump {} log. exception={}", module, e);
        } finally {
            log.info("dum {} log end. cost={}ms", module, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }

    /**
     * 10min一次dump
     *
     * @param module  module
     * @param content content.
     */
    public static void dumpByRate(String module, String content) {
        if (StringUtil.isEmpty(module)) {
            log.warn("module is empty.", module);
            return;
        }

        if (StringUtil.isEmpty(content)) {
            log.warn("{} content is empty.", module);
            return;
        }

        rateLimiterMap.computeIfAbsent(module, key -> {
            // 1 per 10min
            return RateLimiter.create(1.0 / (10 * 60));
        });

        RateLimiter rateLimiter = rateLimiterMap.get(module);

        boolean hasFlag = rateLimiter.tryAcquire(1);
        log.info("try acquire limiter flag={}", hasFlag);
        if (hasFlag) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            try {
                FileUtil.writeFile(getLogFileNameInModule(module), content);
            } catch (Exception e) {
                log.error("fail to dump {} log. exception={}", module, e);
            } finally {
                log.info("dum {} log end. cost={}ms", module, stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }
        }
    }

    /**
     * 生产
     *
     * @param module
     * @return
     */
    private static String getLogFileNameInModule(String module) {
        String logPath = PathUtil.join(PathUtil.getUserHome(), "logs", "sea", module);
        FileUtil.ensureDir(logPath);
        String nowStr = DateUtil.dateStr(new Date(), DateUtil.DATETIME_FORMAT_HUMAN);
        // basePath + "/" + datetime + "_" + pid + "_jstack.log"
        return MessageUtil.format("{}/{}_{}_{}.log", logPath, nowStr, JvmUtil.getPID(), module);
    }


    /**
     * sys.out.print table.
     *
     * @param headers headers
     * @param data    data
     */
    public static void printTable(List<String> headers, List data) {
        printTable(headers, data, 16);
    }

    /**
     * sys.out.print table.
     *
     * @param headers     table header
     * @param data        data
     * @param columnWidth column width
     */
    public static void printTable(List<String> headers, List data, int columnWidth) {
        String format = "%" + columnWidth + "s|";
        for (String header : headers) {
            System.out.printf(format, header);
        }
        System.out.println("");
        for (String header : headers) {
            System.out.print(StringUtils.leftPad("|", columnWidth + 1, "_"));
        }
        System.out.println("");

        if (data == null || data.isEmpty()) {
            return;
        }

        data.stream().forEach(item -> {
            headers.stream().forEach(header -> {
                Object value = null;
                try {
                    value = FieldUtils.readField(item, header, true);
                } catch (Exception e) {
                    log.error("fail to read field.", e);
                    value = "";
                }
                System.out.printf(format, value);
            });
            System.out.println("");
        });

    }
}
