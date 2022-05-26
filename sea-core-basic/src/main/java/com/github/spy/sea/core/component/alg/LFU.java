package com.github.spy.sea.core.component.alg;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * LFU(Least Frequently Used)淘汰一定时期内被访问次数最少的元素。如果元素的一定时间内的访问次数相同时，则比较他们的最新一次的访问时间
 *
 * @author spy
 * @version 1.0 2022/5/26
 * @since 1.0
 */
@Slf4j
public class LFU<K, V> {
    private final int capacity;

    private Map<K, V> cache = new HashMap<>();

    private Map<K, HitRate> count = new HashMap<>();

    public LFU(int capacity) {
        this.capacity = capacity;
    }

    public void put(K key, V value) {
        V v = cache.get(key);
        if (v == null) {
            if (cache.size() == capacity) {
                removeElement();
            }
            count.put(key, new HitRate(key, 1, System.nanoTime()));
        } else {
            addHitCount(key);
        }
        cache.put(key, value);
    }

    public V get(K key) {
        V value = cache.get(key);
        if (value != null) {
            addHitCount(key);
            return value;
        }
        return null;
    }

    public void print() {
        cache.entrySet().forEach(entry -> {
            log.info("{},{}", entry.getKey(), entry.getValue());
        });
    }

    //移除元素
    private void removeElement() {
        HitRate hr = Collections.min(count.values());
        cache.remove(hr.key);
        count.remove(hr.key);
    }

    //更新访问元素状态
    private void addHitCount(K key) {
        HitRate hitRate = count.get(key);
        hitRate.hitCount = hitRate.hitCount + 1;
        hitRate.lastTime = System.nanoTime();
    }

    //内部类
    class HitRate implements Comparable<HitRate> {
        private K key;
        private int hitCount;
        private long lastTime;

        private HitRate(K key, int hitCount, long lastTime) {
            this.key = key;
            this.hitCount = hitCount;
            this.lastTime = lastTime;
        }

        @Override
        public int compareTo(HitRate o) {
            int compare = Integer.compare(this.hitCount, o.hitCount);
            return compare == 0 ? Long.compare(this.lastTime, o.lastTime) : compare;
        }
    }
}
