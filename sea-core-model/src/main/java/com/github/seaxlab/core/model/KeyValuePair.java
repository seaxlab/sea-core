package com.github.seaxlab.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/7/31
 * @since 1.0
 */
@Data
public class KeyValuePair<K, V> implements Serializable {
    private K key;
    private V value;

    public KeyValuePair() {

    }

    public KeyValuePair(K k, V v) {
        this.key = key;
        this.value = v;
    }

    public static <K, V> KeyValuePair<K, V> of(K k, V v) {
        KeyValuePair<K, V> kv = new KeyValuePair<>();
        kv.setKey(k);
        kv.setValue(v);
        return kv;
    }
}
