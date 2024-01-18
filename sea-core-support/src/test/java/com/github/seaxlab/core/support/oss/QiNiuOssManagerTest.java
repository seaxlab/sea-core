package com.github.seaxlab.core.support.oss;

import static com.github.seaxlab.core.test.util.TestUtil.getConfig;

import com.github.seaxlab.core.support.oss.dto.ObjectUrlDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.manager.impl.QiNiuOssManager;
import com.github.seaxlab.core.util.PathUtil;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/14
 * @since 1.0
 */
@Slf4j
public class QiNiuOssManagerTest extends BaseOssManagerTest {

  @Before
  public void before() {
    ENDPOINT = "http://r43e2my4b.hd-bkt.clouddn.com";
    //ENDPOINT = "http://r41l33vb4.hn-bkt.clouddn.com"; // for private test
    ACCESS_KEY = getConfig("sea.oss.qiniuyun.accessKey");
    SECRET_KEY = getConfig("sea.oss.qiniuyun.secretKey");

    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //注意这里是华东地区
    Configuration cfg = new Configuration(Region.region0());
    //
    ossManager = new QiNiuOssManager(auth, cfg);
  }

  // test biz
  @Test
  public void testBucketExist() throws Exception {
    boolean exist = ossManager.checkBucketExist("abc");
    log.info("{}", exist);
  }

  @Test
  public void testCreateBucketIfNeed() {
    //if (!ossManager.checkBucketExist(BUCKET)) {
    ossManager.createBucket(BUCKET);
    //}
  }

  @Test
  public void testUploadObj() {
    ObjectPutRespDTO dto = ossManager.uploadObj(BUCKET, "abcdef", PathUtil.getUserHome() + "/test/gc1.log");
    log.info("dto={}", dto);
  }

  @Test
  public void testGetObjUrl() throws Exception {
    ObjectUrlDTO dto = new ObjectUrlDTO();
    dto.setBucket(BUCKET);
    dto.setKey("abcdef");
    dto.setCustomDomainFlag(true);
    String url = ossManager.getObjUrl(dto);
    log.info("url={}", url);
  }

  @Test
  public void testGetObjSignedUrl() throws Exception {
    String url = ossManager.getObjSignedUrl("spy-private-bucket", "gc1.log", 100);
    log.info("url={}", url);

  }

}
