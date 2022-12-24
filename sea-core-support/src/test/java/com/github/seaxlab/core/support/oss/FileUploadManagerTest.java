package com.github.seaxlab.core.support.oss;

import com.github.seaxlab.core.support.BaseSupportTest;
import com.github.seaxlab.core.support.oss.manager.impl.AliyunFileUploadManager;
import com.github.seaxlab.core.util.IdUtil;
import com.github.seaxlab.core.util.PathUtil;
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
public class FileUploadManagerTest extends BaseSupportTest {

  private AliyunFileUploadManager fileUploadManager;


  private String bucketDefault;

  @BeforeEach
  public void before() {

    String accessKeyId = getPassword("oss_yt_access_key_id");
    String accessKeySecret = getPassword("oss_yt_access_key_secret");
    String endpoint = "oss-cn-hangzhou.aliyuncs.com";

    fileUploadManager = new AliyunFileUploadManager();
    fileUploadManager.setEndpoint(endpoint);
    fileUploadManager.setAccessKeyId(accessKeyId);
    fileUploadManager.setAccessKeySecret(accessKeySecret);

    bucketDefault = getPassword("oss_yt_bucket_default");
//        bucketDefault = "test";
  }

  @Test
  public void run16() throws Exception {
    String filename = IdUtil.shortUUID() + ".png";

    String userHome = PathUtil.getUserHome();
    String fileUrl = fileUploadManager.uploadByFilePath(userHome + "/test/test.png", bucketDefault, filename);
    log.info("fileUrl={}", fileUrl);

    //https://${bucket}.oss-cn-hangzhou.aliyuncs.com/2fc1b6419c75412ab0d1b494fd2d0fd3.png

  }

}
