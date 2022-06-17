package com.github.seaxlab.core.pattern.plugin;

import java.io.Serializable;
import java.util.HashMap;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/4
 * @since 1.0
 */
public class PluginContext implements Serializable {

    private HashMap<String, Object> param = new HashMap<>();


    public void put(String key, Object value) {
        param.put(key, value);
    }

    public Object get(String key) {
        return param.get(key);
    }

    public void remove(String key) {
        param.remove(key);
    }

    public void removeAll() {
        param.clear();
    }

}
