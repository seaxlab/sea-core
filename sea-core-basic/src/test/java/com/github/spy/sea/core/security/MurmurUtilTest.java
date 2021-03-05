package com.github.spy.sea.core.security;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.security.util.MurmurUtil;
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
public class MurmurUtilTest extends BaseCoreTest {

    @Test
    public void test22() throws Exception {
        // 测试均匀性
        int bucketSize = 10;
        Map<Integer, Long> bucketMap = new HashMap<>();
        for (long i = 15000000000l; i < 15000000000l + 10000000; i++) {
            int hashInt = MurmurUtil.hash3_128("" + i) % bucketSize;
            bucketMap.putIfAbsent(hashInt, new Long(0));
            bucketMap.put(hashInt, bucketMap.get(hashInt) + 1);
        }

        log.info("bucketMap={}", bucketMap);
        // 3s
        //{0=999528, -1=501019, -2=500253, 1=498787, -3=499274, 2=499053, -4=500832, 3=499832, 4=499762, -5=501768, -6=500465, 5=500964, 6=500047, -7=500178, -8=500759, 7=498075, -9=499930, 8=498441, 9=501033}
    }

    @Test
    public void test37() throws Exception {
        // 测试均匀性
        int bucketSize = 10;
        Map<Integer, Long> bucketMap = new HashMap<>();
        for (long i = 15000000000l; i < 15000000000l + 10000000; i++) {
            int hashInt = MurmurUtil.hash3_128_positive("" + i) % bucketSize;
            bucketMap.putIfAbsent(hashInt, new Long(0));
            bucketMap.put(hashInt, bucketMap.get(hashInt) + 1);
        }

        log.info("bucketMap={}", bucketMap);
        //3s
        //{0=999528, 1=999806, 2=999306, 3=999106, 4=1000594, 5=1002732, 6=1000512, 7=998253, 8=999200, 9=1000963}
    }
}
