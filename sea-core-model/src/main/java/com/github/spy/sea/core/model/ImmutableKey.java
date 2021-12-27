package com.github.spy.sea.core.model;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/27
 * @since 1.0
 */
@Slf4j
public class ImmutableKey {

    private static final String DEFAULT_SPLIT = ":";

    // store
    private Object[] members;

    /**
     * 构造
     *
     * @param members 成员数组
     */
    public ImmutableKey(Object... members) {
        this.members = members;
    }

    /**
     * 由元素组装方法组装成ImmutableKey
     *
     * @param members
     * @return
     */
    public static ImmutableKey of(Object... members) {
        return new ImmutableKey(members);
    }

    /**
     * 从字符串中解析成ImmutableKey
     *
     * @param keyStr
     * @return
     */
    public static ImmutableKey parse(String keyStr) {
        if (keyStr == null) {
            throw new IllegalArgumentException("args cannot be null");
        }

        return of(keyStr.split(DEFAULT_SPLIT));
    }

    /**
     * 读取值
     *
     * @param index
     * @param <T>
     * @return
     */
    public <T> T get(int index) {
        return (T) members[index];
    }


    //---------override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImmutableKey)) return false;
        ImmutableKey that = (ImmutableKey) o;
        return Arrays.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(members);
    }

    @Override
    public String toString() {
        if (members == null || members.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        Arrays.stream(members).forEach(item -> builder.append(DEFAULT_SPLIT).append(item));

        return builder.substring(1);
    }

}
