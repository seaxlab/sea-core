package com.github.seaxlab.core.support.oss;

import com.github.seaxlab.core.support.oss.dto.ObjectUrlDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
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
    ACCESS_KEY = "4B6wjMVbaIrR5t5vGXaB1mB7NfGWWk_LrSUqMPaQ";
    SECRET_KEY = "WaK_ECcqIC_yy_lS4KT2_2ryGINo-U9vFhG7eLaq";
    OSS_TYPE = OssTypeEnum.QINIU_CLOUD;

    super.before();
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


  @After
  public void after() {
    ossManager.destroy();
  }
}
