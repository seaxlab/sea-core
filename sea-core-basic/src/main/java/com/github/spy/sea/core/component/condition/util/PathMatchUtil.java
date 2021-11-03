package com.github.spy.sea.core.component.condition.util;

import com.google.common.base.Splitter;
import org.springframework.util.AntPathMatcher;

/**
 * The type Path match utils.
 */
public class PathMatchUtil {

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    /**
     * Match boolean.
     *
     * @param matchUrls the ignore urls
     * @param path      the path
     * @return the boolean
     */
    public static boolean match(final String matchUrls, final String path) {
        return Splitter.on(",").omitEmptyStrings().trimResults().splitToList(matchUrls).stream().anyMatch(url -> reg(url, path));
    }

    private static boolean reg(final String pattern, final String path) {
        return MATCHER.match(pattern, path);
    }

}
