package com.github.spy.sea.core.dal.context;

import com.github.spy.sea.core.dal.common.DalConst;
import com.github.spy.sea.core.thread.ThreadContext;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Hint Context
 *
 * @author spy
 * @version 1.0 2021/4/15
 * @since 1.0
 */
@Slf4j
public final class HintContext {

    /**
     * 设置hint
     *
     * @param hint
     */
    public static void put(String hint) {
        if (StringUtil.isEmpty(hint)) {
            log.warn("hint is empty, plz check.");
            return;
        }
        ThreadContext.put(DalConst.SEA_SQL_HINT, hint);
    }

    /**
     * get hint
     *
     * @return
     */
    public static String get() {
        return ThreadContext.get(DalConst.SEA_SQL_HINT);
    }

    /**
     * remove  hint
     */
    public static void remove() {
        ThreadContext.remove(DalConst.SEA_SQL_HINT);
    }
}
