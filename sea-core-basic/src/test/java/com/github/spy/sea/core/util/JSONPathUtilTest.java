package com.github.spy.sea.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.spy.sea.core.BaseCoreTest;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public class JSONPathUtilTest extends BaseCoreTest {

    private String text;
    private JSONObject jsonObj;

    @Before
    public void before() throws Exception {
        super.before();
        InputStream in = getClass().getClassLoader().getResourceAsStream("util/users.json");

        URL url = getClass().getClassLoader().getResource("util/users.json");

//        file:/Users/.../sea-core/sea-core-basic/target/test-classes/util/users.json
        log.info("url={}", url);
        text = Files.toString(new File(url.getFile()), Charsets.UTF_8);
        log.info("text={}", text);

        jsonObj = JSON.parseObject(text);
    }

    @Test
    public void run17() throws Exception {

        Assert.assertEquals(true, JSONPathUtil.contains(jsonObj, "$.users"));

        log.info("{}", JSONPathUtil.get(jsonObj, "$.users"));
    }
}
