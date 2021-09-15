package com.github.spy.sea.core.common;

import com.github.spy.sea.core.config.ConfigurationFactory;
import com.github.spy.sea.core.util.EqualUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Env
 *
 * @author spy
 * @version 1.0 2020/4/1
 * @since 1.0
 */
@Slf4j
public class Env implements Serializable {

    public static final String LOCAL = "local";
    public static final String DEV = "dev";
    public static final String TEST = "test";

    public static final String SIT = "sit";
    public static final String UAT = "uat";
    public static final String PRE = "pre";
    public static final String PRO = "pro";

    private Env() {
    }

    /**
     * check is local env
     *
     * @return
     */
    public static boolean isLocalMode() {
        return EqualUtil.isEq(LOCAL, ConfigurationFactory.getInstance().getString(CoreConst.KEY_SEA_DEV_MODE, PRO), false);
    }

    /**
     * check is pre env.
     *
     * @return boolean
     */
    public static boolean isPreMode() {
        return EqualUtil.isEq(PRE, ConfigurationFactory.getInstance().getString(CoreConst.KEY_SEA_DEV_MODE, PRO), false);
    }

    /**
     * check is pro env
     *
     * @return
     */
    public static boolean isProMode() {
        return EqualUtil.isEq(PRO, ConfigurationFactory.getInstance().getString(CoreConst.KEY_SEA_DEV_MODE, PRO), false);
    }

    /**
     * non pro mode.
     *
     * @return
     */
    public static boolean isNonProMode() {
        return !isProMode();
    }

}
