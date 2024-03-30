package com.github.seaxlab.core.support.oss;

import com.github.seaxlab.core.support.oss.manager.impl.MinioOssManager;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/5/11
 * @since 1.0
 */
@Slf4j
public class MinioOssManagerTest extends BaseOssManagerTest {

  @Override
  public void before() {
    String endpoint = "";
    String accessKey = "";
    String secretKey = "";
    //
    MinioClient client = MinioClient.builder() //
                                    .endpoint(endpoint) //
                                    .credentials(accessKey, secretKey) //
                                    .build();
    ossManager = new MinioOssManager(client);
  }
}
