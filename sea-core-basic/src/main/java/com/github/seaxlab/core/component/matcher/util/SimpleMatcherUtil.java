package com.github.seaxlab.core.component.matcher.util;

import com.github.seaxlab.core.component.matcher.SimpleMatcher;
import com.github.seaxlab.core.component.matcher.impl.DefaultSimpleMatcher;
import lombok.extern.slf4j.Slf4j;

/**
 * 支持*?两种通配符
 *
 * @author spy
 * @version 1.0 2021/12/28
 * @since 1.0
 */
@Slf4j
public final class SimpleMatcherUtil {

    /**
     * match
     *
     * @param target
     * @param wildCard
     * @return
     */
    public static boolean match(String target, String wildCard) {
        SimpleMatcher matcher = new DefaultSimpleMatcher();
        return matcher.match(target, wildCard);
    }

}
