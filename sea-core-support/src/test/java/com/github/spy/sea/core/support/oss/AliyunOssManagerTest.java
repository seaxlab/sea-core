package com.github.spy.sea.core.support.oss;

import com.github.spy.sea.core.model.Result;
import com.github.spy.sea.core.support.oss.enums.OssTypeEnum;
import com.github.spy.sea.core.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Slf4j
public class AliyunOssManagerTest extends BaseOssManagerTest {

    @Before
    public void before() {
        OSS_TYPE = OssTypeEnum.ALI_YUN;
        ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
        ACCESS_KEY = "8PIhaKLfrSBFvK1f";
        SECRET_KEY = "uK1uKmOtX2HP91kpVWRixWEiCh933J";

        super.before();
    }

    // test biz
    @Test
    public void testCreateBucketIfNeed() {
        //if (!ossManager.checkBucketExist(BUCKET)) {
        ossManager.createBucket(BUCKET);
        //}
    }

    @Test
    public void testUploadObj() {
        Result ret = ossManager.uploadObj(BUCKET, "abcdef", PathUtil.getUserHome() + "/test/gc1.log");
        log.info("ret={}", ret);
    }


    @After
    public void after() {
        ossManager.destroy();
    }
}
