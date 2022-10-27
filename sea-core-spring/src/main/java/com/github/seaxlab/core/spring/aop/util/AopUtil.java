package com.github.seaxlab.core.spring.aop.util;

import com.github.seaxlab.core.common.SymbolConst;
import com.github.seaxlab.core.util.MessageUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Slf4j
public class AopUtil {

    private static final String EXECUTION_ONE = "execution(public * {}..*.*(..))";


    /**
     * get on execution by or
     *
     * @param packagePaths
     * @return
     */
    public static String getOneExecutionByOr(String packagePaths) {
        if (StringUtil.isBlank(packagePaths)) {
            return StringUtil.empty();
        }
        String[] packages;
        if (packagePaths.contains(SymbolConst.COMMA)) {
            packages = packagePaths.split(SymbolConst.COMMA);
        } else {
            packages = new String[]{packagePaths};
        }
        if (packages.length == 1) {
            return "(" + MessageUtil.format(EXECUTION_ONE, packages[0]) + ")";
        }

        String expression = "";
        if (packages.length > 1) {
            expression = MessageUtil.format(EXECUTION_ONE, packages[0]);
            for (int i = 1; i < packages.length; i++) {
                String item = packages[i];
                if (StringUtil.isBlank(item)) {
                    continue;
                }
                expression += " or " + MessageUtil.format(EXECUTION_ONE, item.trim());
            }
        }
        return "(" + expression + ")";
    }
}
