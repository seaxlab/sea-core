package com.github.seaxlab.core.common;

import com.github.seaxlab.core.util.VersionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/12
 * @since 1.0
 */
@Slf4j
public class Version {
    private Version() {
    }

    private static final String VERSION = VersionUtil.getVersion(Version.class, "1.0.0"); // NOSONAR

    public static String getVersion() {
        return VERSION;
    }
}
