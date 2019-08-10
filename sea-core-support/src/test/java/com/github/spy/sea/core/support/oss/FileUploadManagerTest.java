package com.github.spy.sea.core.support.oss;

import com.github.spy.sea.core.config.ConfigurationFactory;
import com.github.spy.sea.core.support.oss.manager.impl.AliyunFileUploadManager;
import com.github.spy.sea.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-07
 * @since 1.0
 */
@Slf4j
public class FileUploadManagerTest {

    private AliyunFileUploadManager fileUploadManager;


    private String bucketDefault;

    @BeforeEach
    public void before() {

        String accessKeyId = "8PIhaKLfrSBFvK1f";
        String accessKeySecret = "uK1uKmOtX2HP91kpVWRixWEiCh933J";
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";

        fileUploadManager = new AliyunFileUploadManager();
        fileUploadManager.setEndpoint(endpoint);
        fileUploadManager.setAccessKeyId(accessKeyId);
        fileUploadManager.setAccessKeySecret(accessKeySecret);

        bucketDefault = "yuantu-hz-img";
//        bucketDefault = "test";
    }

    @Test
    public void run16() throws Exception {
        String filename = IdUtil.shortUUID() + ".png";

        String userhome = ConfigurationFactory.getInstance().getString("user.home");
        String fileUrl = fileUploadManager.uploadByFilePath(userhome + "/test/test.png", bucketDefault, filename);
        log.info("fileUrl={}", fileUrl);

        //https://yuantu-hz-img.oss-cn-hangzhou.aliyuncs.com/2fc1b6419c75412ab0d1b494fd2d0fd3.png

    }

}
