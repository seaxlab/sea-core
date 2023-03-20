package com.github.seaxlab.core.support.oss.manager;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.support.oss.dto.BucketCreateDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectQueryDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectUploadDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectUrlDTO;
import com.github.seaxlab.core.support.oss.dto.OssConfig;
import com.github.seaxlab.core.support.oss.dto.response.BucketRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectRespDTO;
import com.github.seaxlab.core.support.oss.enums.AclEnum;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
@Slf4j
public abstract class AbstractOssManager implements OssManager {

  protected OssConfig ossConfig;

  @Override
  public void init(OssConfig config) {
    Precondition.checkNotNull(config);
    Precondition.checkNotEmpty(config.getEndpoint(), "Endpoint cannot be empty");
    Precondition.checkNotEmpty(config.getAccessKey(), "AccessKey cannot be empty");
    Precondition.checkNotEmpty(config.getSecretKey(), "SecretKey cannot be empty");
    log.info("init {} oss manager, config={}", getType(), config);

    this.ossConfig = config;
    _init(config);
    log.info("init {} oss manager successfully", getType());
  }

  @Override
  public void destroy() {
    log.info("destroy {} oss manager", getType());
    _destroy();
  }

  @Override
  public String getType() {
    return OssTypeEnum.UNKNOWN.getCode();
  }

  @Override
  public boolean checkBucketExist(String bucket) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    return _checkBucketExist(bucket);
  }

  @Override
  public void createBucket(String bucket) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    log.info("create bucket={}", bucket);
    _createBucket(bucket);
    log.info("create bucket successfully");
  }

  @Override
  public void createBucket(BucketCreateDTO dto) {
    Precondition.checkNotEmpty(dto.getName(), "bucket cannot be empty");
    log.info("create bucket={}", dto.getName());
    if (dto.getAclEnum() == null) {
      dto.setAclEnum(AclEnum.PRIVATE);
    }
    _createBucket(dto);
    log.info("create bucket successfully");
  }

  @Override
  public void deleteBucket(String bucket) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    log.info("try to delete bucket={}", bucket);

    _deleteBucket(bucket);
    log.info("delete bucket successfully");
  }

  @Override
  public List<BucketRespDTO> queryBuckets() {
    return _queryBuckets();
  }

  @Override
  public boolean checkObjExist(String bucket, String key) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");

    return _checkObjExist(bucket, key);
  }

  @Override
  public ObjectPutRespDTO uploadObj(String bucket, String key, String filePath) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    Precondition.checkNotEmpty(filePath, "filePath cannot be empty");
    log.info("upload obj bucket={},key={},filePath={}", bucket, key, filePath);

    ObjectPutRespDTO dto = _uploadObj(bucket, key, filePath);
    log.info("upload obj successfully");
    return dto;
  }

  @Override
  public ObjectPutRespDTO uploadObj(String bucket, String key, File file) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    Precondition.checkNotNull(file, "file cannot be empty");
    log.info("upload obj bucket={},key={} by file", bucket, key);

    return _uploadObj(bucket, key, file);
  }

  @Override
  public ObjectPutRespDTO uploadObj(String bucket, String key, InputStream inputStream) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    Precondition.checkNotNull(inputStream, "inputStream cannot be empty");
    log.info("upload obj bucket={},key={} by file", bucket, key);
    return _uploadObj(bucket, key, inputStream);
  }

  @Override
  public ObjectPutRespDTO uploadObj(ObjectUploadDTO dto) {
    Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
    Precondition.checkNotEmpty(dto.getKey(), "key cannot be empty");
    if (dto.getFile() == null && dto.getInputStream() == null) {
      ExceptionHandler.publishMsg("file, inputStream不能同时为空");
    }
    if (dto.getAclEnum() == null) {
      dto.setAclEnum(AclEnum.PRIVATE);
    }
    log.info("upload obj bucket={},key={} by file", dto.getBucket(), dto.getKey());

    return _uploadObj(dto);
  }

  @Override
  public String getObjUrl(ObjectUrlDTO dto) {
    Precondition.checkNotNull(dto);
    Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
    Precondition.checkNotEmpty(dto.getKey(), "key cannot be empty");

    return _getObjUrl(dto);
  }

  @Override
  public String getObjSignedUrl(String bucket, String key, long expireSeconds) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");

    expireSeconds = expireSeconds < 0 ? 600 /*second*/ : expireSeconds;

    return _getObjSignedUrl(bucket, key, expireSeconds);
  }

  @Override
  public String getObjSignedUrl(ObjectSignUrlDTO dto) {
    Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
    Precondition.checkNotEmpty(dto.getKey(), "key cannot be empty");

    if (dto.getExpireSeconds() < 0) {
      dto.setExpireSeconds(600);
    }

    return _getObjSignedUrl(dto);
  }

  @Override
  public void downloadObj(String bucket, String key, String filePath) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    log.info("download obj bucket={},key={},filePath={}", bucket, key, filePath);

    _downloadObj(bucket, key, filePath);
    log.info("download obj successfully");
  }

  @Override
  public void deleteObj(String bucket, String key) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    log.info("delete obj bucket={},key={}", bucket, key);

    _deleteObj(bucket, key);
    log.info("delete obj successfully");
  }

  @Override
  public void deleteObjs(String bucket, List<String> keys) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(keys, "keys cannot be empty");
    log.info("delete objs bucket={},keys={}", bucket, keys);

    _deleteObjs(bucket, keys);
    log.info("delete objs successfully");
  }

  @Override
  public List<ObjectRespDTO> queryObjs(ObjectQueryDTO dto) {
    Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
    if (dto.getMaxKeys() <= 0 || dto.getMaxKeys() >= 1000) {
      log.warn("max keys[{}] is invalid", dto.getMaxKeys());
      dto.setMaxKeys(100);
    }

    return _queryObjs(dto);
  }


  // ------------- simple overwrite
  public void _init(OssConfig config) {
  }

  public void _destroy() {
  }

  public boolean _checkBucketExist(String bucket) {
    return false;
  }

  public void _createBucket(String bucket) {
  }

  public void _createBucket(BucketCreateDTO dto) {
  }

  public void _deleteBucket(String bucket) {
  }

  public List<BucketRespDTO> _queryBuckets() {
    return new ArrayList<>();
  }

  public boolean _checkObjExist(String bucket, String key) {
    return false;
  }

  public ObjectPutRespDTO _uploadObj(String bucket, String key, String filePath) {
    return null;
  }

  public ObjectPutRespDTO _uploadObj(String bucket, String key, File file) {
    return null;
  }

  public ObjectPutRespDTO _uploadObj(String bucket, String key, InputStream inputStream) {
    return null;
  }

  public ObjectPutRespDTO _uploadObj(ObjectUploadDTO dto) {
    return null;
  }

  public String _getObjUrl(ObjectUrlDTO dto) {
    String url = "";
    if (dto.isCustomDomainFlag()) {
      url = ossConfig.getEndpoint() + "/" + dto.getKey();
    } else {
      // default url format: http://bucket.xx.com/key
      String bucketUrl = StringUtils.replace(ossConfig.getEndpoint(), "://", "://" + dto.getBucket() + ".");
      url = bucketUrl + "/" + dto.getKey();
    }

    return url;
  }

  public String _getObjSignedUrl(String bucket, String key, long expireSeconds) {
    return "";
  }

  public String _getObjSignedUrl(ObjectSignUrlDTO dto) {
    return "";
  }

  public void _downloadObj(String bucket, String key, String filePath) {
  }

  public void _deleteObj(String bucket, String key) {
  }

  public void _deleteObjs(String bucket, List<String> keys) {
  }

  public List<ObjectRespDTO> _queryObjs(ObjectQueryDTO dto) {
    return new ArrayList<>();
  }

}
