package com.github.seaxlab.core.support.oss;

import static com.github.seaxlab.core.test.util.TestUtil.getConfig;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.manager.impl.AliyunOssManager;
import com.github.seaxlab.core.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
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
    ENDPOINT = getConfig("sea.oss.aliyun.endpoint");
    ACCESS_KEY = getConfig("sea.oss.aliyun.accessKey");
    SECRET_KEY = getConfig("sea.oss.aliyun.secretKey");

    CredentialsProvider credentialsProvider = new DefaultCredentialProvider(ACCESS_KEY, SECRET_KEY);
    OSSClient client = new OSSClient(ENDPOINT, credentialsProvider, null);
    ossManager = new AliyunOssManager(client);
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
    ObjectPutRespDTO dto = ossManager.uploadObj(BUCKET, "abcdef", PathUtil.getUserHome() + "/test/gc1.log");
    log.info("ret={}", dto);
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

}
