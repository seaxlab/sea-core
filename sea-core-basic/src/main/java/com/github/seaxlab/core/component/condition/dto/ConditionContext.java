package com.github.seaxlab.core.component.condition.dto;

import java.util.HashMap;

/**
 * condition context, you can put any object here.
 *
 * @author spy
 * @version 1.0 2021/11/3
 * @since 1.0
 */
public class ConditionContext {

    private final HashMap<String, Object> map = new HashMap<>();


    //
    public void put(String key, Object obj) {
        map.put(key, obj);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public Object getOrDefault(String key, Object defaultObj) {
        return map.getOrDefault(key, defaultObj);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public void removeAll() {
        map.clear();
    }

}
