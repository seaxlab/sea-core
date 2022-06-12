package com.github.seaxlab.core.alg;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.alg.LFU;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/26
 * @since 1.0
 */
@Slf4j
public class LFUTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        LFU<Integer, Integer> cache = new LFU<>(3);
        cache.put(2, 2);
        cache.put(1, 1);

        System.out.println(cache.get(2));
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));

        cache.put(3, 3);
        cache.put(4, 4);

        //1、2元素都有访问次数，放入3后缓存满，加入4时淘汰3
        System.out.println(cache.get(3));
        System.out.println(cache.get(2));
        //System.out.println(cache.get(1));
        System.out.println(cache.get(4));

        cache.put(5, 5);
        //目前2访问2次，1访问一次，4访问一次，由于4的时间比较新，放入5的时候移除1元素。
        System.out.println("-=-=-=-");
        cache.print();
    }
}
