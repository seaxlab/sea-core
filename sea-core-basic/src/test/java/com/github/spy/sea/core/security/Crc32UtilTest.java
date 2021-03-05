package com.github.spy.sea.core.security;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.security.util.Crc32Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/5
 * @since 1.0
 */
@Slf4j
public class Crc32UtilTest extends BaseCoreTest {

    @Test
    public void test22() throws Exception {
        // 测试均匀性
        int bucketSize = 10;
        Map<Integer, Long> bucketMap = new HashMap<>();
        for (long i = 15000000000l; i < 15000000000l + 10000000; i++) {
            int hashInt = Crc32Util.hash("" + i) % bucketSize;
            bucketMap.putIfAbsent(hashInt, new Long(0));
            bucketMap.put(hashInt, bucketMap.get(hashInt) + 1);
        }

        log.info("bucketMap={}", bucketMap);
        // 3s
        //{-1=499492, 0=999769, -2=499474, 1=500300, 2=500098, -3=499983, -4=499976, 3=500966, 4=499846, -5=499803, -6=500678, 5=499291, 6=500491, -7=500262, 7=499425, -8=499875, 8=499793, -9=500460, 9=500018}
    }

    @Test
    public void test37() throws Exception {
        // 测试均匀性
        int bucketSize = 10;
        Map<Integer, Long> bucketMap = new HashMap<>();
        for (long i = 15000000000l; i < 15000000000l + 10000000; i++) {
            int hashInt = Crc32Util.hash("" + i) % bucketSize;
            hashInt = Math.abs(hashInt);
            bucketMap.putIfAbsent(hashInt, new Long(0));
            bucketMap.put(hashInt, bucketMap.get(hashInt) + 1);
        }

        log.info("bucketMap={}", bucketMap);
        //3s
        //{0=999769, 1=999792, 2=999572, 3=1000949, 4=999822, 5=999094, 6=1001169, 7=999687, 8=999668, 9=1000478}
    }
}
