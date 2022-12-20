package com.github.seaxlab.core.component.buffertrigger.common;

/**
 * @author w.vela Created on 2020-06-08.
 */
public interface BackPressureListener<T> {

    void onHandle(T element);
}
