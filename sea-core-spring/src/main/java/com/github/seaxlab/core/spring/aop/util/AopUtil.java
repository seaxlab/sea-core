package com.github.seaxlab.core.spring.aop.util;

import com.github.seaxlab.core.common.SymbolConst;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.spring.aop.AopExpressionEnum;
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

    /**
     * get one execution
     *
     * @param packagePath
     * @return
     */
    public static String getOneExecution(AopExpressionEnum expressionEnum, String packagePath) {
        Precondition.checkNotEmpty("packagePath", "packagePath cannot be empty.");
        return "(" + MessageUtil.format(expressionEnum.getCode(), packagePath.trim()) + ")";
    }

    /**
     * get on execution by or
     *
     * @param packagePaths
     * @return
     */
    public static String getOneExecutionByOr(AopExpressionEnum expressionEnum, String packagePaths) {
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
            return "(" + MessageUtil.format(expressionEnum.getCode(), packages[0]) + ")";
        }

        String expression = "";
        if (packages.length > 1) {
            expression = MessageUtil.format(expressionEnum.getCode(), packages[0]);
            for (int i = 1; i < packages.length; i++) {
                String item = packages[i];
                if (StringUtil.isBlank(item)) {
                    continue;
                }
                expression += " or " + MessageUtil.format(expressionEnum.getCode(), item.trim());
            }
        }
        return "(" + expression + ")";
    }
}
