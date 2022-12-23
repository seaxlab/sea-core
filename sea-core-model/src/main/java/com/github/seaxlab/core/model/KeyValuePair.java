package com.github.seaxlab.core.model;

import java.io.Serializable;
import lombok.Data;

/**
 * key value pair
 *
 * @author spy
 * @version 1.0 2020/7/31
 * @since 1.0
 */
@Data
public class KeyValuePair<K, V> implements Serializable {

  private K key;
  private V value;

  private KeyValuePair() {

  }

  public KeyValuePair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public static <K, V> KeyValuePair<K, V> of(K key, V value) {
    KeyValuePair<K, V> kv = new KeyValuePair<>();
    kv.setKey(key);
    kv.setValue(value);
    return kv;
  }
}
