package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.UUID;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-06-19
 * @since 1.0
 */
@Slf4j
public final class RandomUtil {


    /**
     * 生成UUID
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }


    /**
     * 生成不带中划线的UUID
     *
     * @return
     */
    public static String shortUUID() {
        return uuid().replace("-", "");
    }

    /**
     * create numeric
     *
     * @param count
     * @return
     */
    public static String numeric(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    /**
     * create letters
     *
     * @param count
     * @return
     */
    public static String alphabetic(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    /**
     * get random one of
     *
     * @param candidates
     * @return
     */
    public static String oneOf(String... candidates) {
        Preconditions.checkNotNull(candidates, "candidates can not be null");
        return candidates[RandomUtils.nextInt(0, candidates.length)];
    }


    /**
     * get random one of
     *
     * @param candidates
     * @return
     */
    public static String oneOf(List<String> candidates) {
        Preconditions.checkNotNull(candidates, "candidates can not be null");
        return candidates.get(RandomUtils.nextInt(0, candidates.size()));
    }
}
