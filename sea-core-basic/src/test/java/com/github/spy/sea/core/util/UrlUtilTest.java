package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/3
 * @since 1.0
 */
@Slf4j
public class UrlUtilTest extends BaseCoreTest {
    @Test
    public void parseTest() throws Exception {

        String url = "www.baidu.com?a=1&b=2&password=%26abcd";
        log.info("props={}", UrlUtil.parse(url));

        url = "https://www.baidu.com:1000?a=1";
        log.info("props={}", UrlUtil.parse(url));
    }

    @Test
    public void joinTest() throws Exception {
        Assert.assertEquals("http://www.a.com/abc/csd", UrlUtil.join("http://www.a.com", "abc", "csd"));
        Assert.assertEquals("http://www.a.com/abc/csd", UrlUtil.join("http://www.a.com", "/abc", "csd"));
        Assert.assertEquals("http://www.a.com/abc/csd", UrlUtil.join("http://www.a.com/", "/abc", "csd"));
        Assert.assertEquals("http://www.a.com/abc/csd", UrlUtil.join("http://www.a.com/", "/abc", "/csd"));
        Assert.assertEquals("http://www.a.com/abc/csd", UrlUtil.join("http://www.a.com/", "/abc/", "/csd"));
    }
}
