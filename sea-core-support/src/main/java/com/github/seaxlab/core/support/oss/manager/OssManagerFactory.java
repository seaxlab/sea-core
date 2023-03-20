package com.github.seaxlab.core.support.oss.manager;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.support.oss.manager.impl.AliyunOssManager;
import com.github.seaxlab.core.support.oss.manager.impl.HuaWeiCloudOssManager;
import com.github.seaxlab.core.support.oss.manager.impl.MinioOssManager;
import com.github.seaxlab.core.support.oss.manager.impl.QiNiuOssManager;
import com.github.seaxlab.core.support.oss.manager.impl.TencentOssManager;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Slf4j
public class OssManagerFactory {

  public static OssManager get(OssTypeEnum ossTypeEnum) {
    Precondition.checkNotNull(ossTypeEnum);

    OssManager ossManager;
    switch (ossTypeEnum) {
      case ALI_YUN:
        ossManager = new AliyunOssManager();
        break;
      case HUAWEI_CLOUD:
        ossManager = new HuaWeiCloudOssManager();
        break;
      case MINIO:
        ossManager = new MinioOssManager();
        break;
      case QINIU_CLOUD:
        ossManager = new QiNiuOssManager();
        break;
      case TENCENT_CLOUD:
        ossManager = new TencentOssManager();
        break;
      case UNKNOWN:
      default:
        log.warn("unsupported oss={}", ossTypeEnum);
        ossManager = null;
        ExceptionHandler.publishMsg("不支持的oss");
        break;
    }

    return ossManager;
  }
}
