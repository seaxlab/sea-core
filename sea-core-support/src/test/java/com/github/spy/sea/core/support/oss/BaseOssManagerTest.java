package com.github.spy.sea.core.support.oss;

import com.github.spy.sea.core.support.BaseSupportTest;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.enums.OssTypeEnum;
import com.github.spy.sea.core.support.oss.manager.OssManager;
import com.github.spy.sea.core.support.oss.manager.OssManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Slf4j
public class BaseOssManagerTest extends BaseSupportTest {

    protected String ENDPOINT = "";
    protected String ACCESS_KEY = "";
    protected String SECRET_KEY = "";

    protected String BUCKET = "test";

    protected OssManager ossManager;


    @Before
    public void before() {
        OssConfig config = new OssConfig();
        config.setEndpoint(ENDPOINT);
        config.setAccessKey(ACCESS_KEY);
        config.setSecretKey(SECRET_KEY);

        ossManager = OssManagerFactory.get(OssTypeEnum.HUAWEI_CLOUD);
        ossManager.init(config);
    }

    // test biz
    public void createBucketIfNeed() {
        if (!ossManager.checkBucketExist(BUCKET)) {
            ossManager.createBucket(BUCKET);
        }
    }

    public void uploadObj(String bucket, String key, String filePath) {
        ossManager.uploadObj(bucket, key, filePath);
    }


    @After
    public void after() {
        ossManager.destroy();
    }


}
