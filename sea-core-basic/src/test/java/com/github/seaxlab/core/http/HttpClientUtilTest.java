package com.github.seaxlab.core.http;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.http.dto.HttpUploadDTO;
import com.github.seaxlab.core.http.simple.HttpClientUtil;
import com.github.seaxlab.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-24
 * @since 1.0
 */
@Slf4j
public class HttpClientUtilTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {
    String result = HttpClientUtil.get(IP_URL);

    log.info("result={}", result);
  }

  String str = "[{\"endpoint\": \"user-service\", \"metric\": \"test-metric\", \"timestamp\": 1, \"step\": 60, \"value\": 1, \"counterType\": \"GAUGE\", \"tags\": \"idc=lg,loc=beijing\"}]";

  @Test
  public void run27() throws Exception {
    Result ret = HttpClientUtil.postJSONSafe("http://127.0.0.1:1988/v1/push", str);

    log.info("ret={}", ret);
  }

  @Test
  public void run36() throws Exception {
    byte[] by = str.getBytes(StandardCharsets.UTF_8);

    String str = new String(by, StandardCharsets.UTF_8);

    log.info("str={}", str);
  }


  @Test
  public void uploadTet() throws Exception {
    String userHome = getUserHome();

    HttpUploadDTO uploadDTO = new HttpUploadDTO();
    uploadDTO.setUrl("http://httpbin.org/post");

    File testFile = new File(userHome + "/test/qr.png");

    Map<String, File> fileMap = new HashMap<>();
    fileMap.put("abc", testFile);

    uploadDTO.setFileFieldMap(fileMap);

    Result result = HttpClientUtil.upload(uploadDTO);
    log.info("result={}", result);
  }

}
