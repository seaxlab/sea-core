package com.github.seaxlab.core.cache.redis.redisson.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

/**
 * lock util
 *
 * @author spy
 * @version 1.0 2022/09/01
 * @since 1.0
 */
@Slf4j
public class LockUtil {

    private LockUtil() {
    }

    /**
     * unlock
     *
     * @param lock
     */
    public static void unlock(RLock lock) {
        if (lock == null) {
            return;
        }
        try {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception e) {
            log.error("fail to unlock", e);
        }
    }

}
