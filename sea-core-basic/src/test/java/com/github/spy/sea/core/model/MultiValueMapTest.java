package com.github.spy.sea.core.model;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/1
 * @since 1.0
 */
@Slf4j
public class MultiValueMapTest extends BaseCoreTest {

    @Test
    public void ArrayListMapTest() throws Exception {
        MultiValueMap<String, String> map = new DefaultMultiValueMap<>();
        map.add("aa", "abc");
        map.add("aa", "def");
        log.info("map={}", map);
    }

    @Test
    public void test17() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("aa", "abc");
        map.add("aa", "def");
        log.info("map={}", map);
    }
}
