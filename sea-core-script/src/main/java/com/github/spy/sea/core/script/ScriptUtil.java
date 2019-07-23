package com.github.spy.sea.core.script;

import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 脚本引擎工具
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
@Slf4j
public class ScriptUtil {

    /**
     * 获取脚本引擎
     *
     * @param name
     * @return
     */
    public static ScriptEngine getScriptEngine(String name) {
        return new ScriptEngineManager().getEngineByName(name);
    }
}
