package com.github.seaxlab.core.pattern.plugin;

import com.github.seaxlab.core.annotation.Beta;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/4
 * @since 1.0
 */
@Beta
public interface Plugin {

    /**
     * execute biz
     *
     * @param context
     * @param chain
     */
    void execute(PluginContext context, PluginChain chain);

    /**
     * return plugin order .
     * This attribute To determine the plugin execution order in the same type plugin.
     *
     * @return int order
     */
    int getOrder();

    /**
     * acquire plugin name.
     *
     * @return plugin name.
     */
    default String named() {
        return "";
    }

    /**
     * plugin is execute.
     * if return true this plugin can not execute.
     *
     * @param context the current server exchange
     * @return default false.
     */
    default boolean skip(PluginContext context) {
        return false;
    }
}
