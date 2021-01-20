package com.github.spy.sea.core.function.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/20
 * @since 1.0
 */
@Slf4j
public class DefaultSnowflakeIdWorker {
    private static final SnowflakeIdWorker idWorker;

    static {
        idWorker = new SnowflakeIdWorker(getWorkId(), getDataCenterId());
    }

    /**
     * instance
     *
     * @return
     */
    public static SnowflakeIdWorker instance() {
        return idWorker;
    }

    /**
     * 静态工具类
     *
     * @return
     */
    public static synchronized Long generateId() {
        long id = idWorker.nextId();
        return id;
    }


    private static Long getWorkId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for (int b : ints) {
                sums += b;
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            return RandomUtils.nextLong(0, 31);
        }
    }

    private static Long getDataCenterId() {
        String hostname = SystemUtils.getHostName();
        if (StringUtils.isEmpty(hostname)) {
            try {
                hostname = Inet4Address.getLocalHost().getHostName();
            } catch (Exception e) {
                log.error("fail to get hostname", e);
            }
        }
        int[] ints = StringUtils.toCodePoints(hostname);
        int sums = 0;
        for (int i : ints) {
            sums += i;
        }
        return (long) (sums % 32);
    }

}
