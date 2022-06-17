package com.github.seaxlab.core.component.matcher;

/**
 * 只支持?和*匹配
 * <li>
 * ?:单个匹配
 * </li>
 *
 * <li>
 * *: 多个字符匹配
 * </li>
 *
 * @author spy
 * @version 1.0 2021/12/28
 * @since 1.0
 */
public interface SimpleMatcher {

    /**
     * 判断是否匹配
     *
     * @param target
     * @param wildcard
     * @return
     */
    boolean match(String target, String wildcard);

}
