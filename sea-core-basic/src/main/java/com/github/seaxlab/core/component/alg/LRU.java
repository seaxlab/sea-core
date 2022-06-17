package com.github.seaxlab.core.component.alg;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU的实现比较简单，因为java中的LinkedHashMap有很多特点正好适合LRU的思想。<br/>
 * LRU（least recently used）最近最少使用，首先淘汰最长时间未被使用的页面。<br/>
 * 使用链表实现，当一个元素被访问了，把元素移动到链表的顶端，插入新元素也放到顶端，当缓存数量到达限制，直接从链表底端移除元素<br/>
 * <p>
 * LinkedHashMap有一个accessOrder的参数正好和LRU的思路相契合，这里使用0.75的默认加载因子<br/>
 * （加载因子过小空间利用率低，冲突减少，访问速度快，加载因子过大，反之。加载因子过大，当执行put操作，两个对象的hashcode相同时要操作链表，相应的get操作是也要操作链表，这样就使得访问变慢。）<br/>
 * removeEldestEntry方法当结果返回为true时，它会清除map中的最老元素。以实现LRU的算法。<br/>
 *
 * @author spy
 * @version 1.0 2022/5/26
 * @since 1.0
 */
@Slf4j
public class LRU<K, V> extends LinkedHashMap<K, V> {

    private final int MAX_SIZE;

    public LRU(int capacity) {
        super(8, 0.75f, true);
        this.MAX_SIZE = capacity;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        if (size() > MAX_SIZE) {
            if (log.isDebugEnabled()) {
                log.debug("remove entry: {}", eldest.getValue());
            }
        }
        return size() > MAX_SIZE;
    }
}
