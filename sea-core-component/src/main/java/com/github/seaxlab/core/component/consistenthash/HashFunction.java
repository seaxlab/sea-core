package com.github.seaxlab.core.component.consistenthash;

/**
 * Hash String to long value
 */
public interface HashFunction {
    long hash(String key);
}
