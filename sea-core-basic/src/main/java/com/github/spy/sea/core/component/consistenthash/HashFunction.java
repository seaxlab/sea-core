package com.github.spy.sea.core.component.consistenthash;

/**
 * Hash String to long value
 */
public interface HashFunction {
    long hash(String key);
}
