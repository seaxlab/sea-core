package com.github.seaxlab.core.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterConfig;
import java.util.HashSet;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/10
 * @since 1.0
 */
@Slf4j
public class BaseWebFilter {

    protected Set<String> excludeUrls;
    protected Set<String> excludePrefixes;

    /**
     * init exclude config.
     * must be in Filter.init
     *
     * @param filterConfig
     */
    protected void initExcludeConfig(FilterConfig filterConfig) {
        String excludeStr = filterConfig.getInitParameter("exclude");

        if (excludeStr != null) {
            excludeUrls = new HashSet<>();
            String[] excludeUrlArray = excludeStr.split(";");

            for (String s : excludeUrlArray) {
                int index = s.indexOf('*');

                if (index > 0) {
                    if (excludePrefixes == null) {
                        excludePrefixes = new HashSet<>();
                    }
                    excludePrefixes.add(s.substring(0, index));
                } else {
                    this.excludeUrls.add(s);
                }
            }
        }
    }


    /**
     * check path is exclude.
     *
     * @param path
     * @return
     */
    protected boolean isExcludePath(String path) {
        try {
            boolean exclude = excludeUrls != null && excludeUrls.contains(path);

            if (!exclude && excludePrefixes != null) {
                for (String prefix : excludePrefixes) {
                    if (path.startsWith(prefix)) {
                        exclude = true;
                        break;
                    }
                }
            }
            return exclude;
        } catch (Exception e) {
            return false;
        }
    }
}
