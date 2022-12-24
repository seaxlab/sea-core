package com.github.seaxlab.core.support.oss.manager;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.support.oss.dto.*;
import com.github.seaxlab.core.support.oss.enums.AclEnum;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.support.oss.vo.BucketVO;
import com.github.seaxlab.core.support.oss.vo.ObjectPutVO;
import com.github.seaxlab.core.support.oss.vo.ObjectVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;

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
  public Result createBucket(String bucket) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    log.info("create bucket={}", bucket);

    Result result = _createBucket(bucket);

    if (result.isOk()) {
      log.info("create bucket successfully");
    }

    return result;
  }

  @Override
  public Result<Boolean> createBucket(BucketCreateDTO dto) {
    Precondition.checkNotEmpty(dto.getName(), "bucket cannot be empty");
    log.info("create bucket={}", dto.getName());
    if (dto.getAclEnum() == null) {
      dto.setAclEnum(AclEnum.PRIVATE);
    }
    Result result = _createBucket(dto);

    if (result.isOk()) {
      log.info("create bucket successfully");
    }

    return result;
  }

  @Override
  public Result deleteBucket(String bucket) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    log.info("try to delete bucket={}", bucket);

    Result result = _deleteBucket(bucket);
    if (result.isOk()) {
      log.info("delete bucket successfully");
    }

    return result;
  }

  @Override
  public Result<List<BucketVO>> queryBuckets() {
    return _queryBuckets();
  }

  @Override
  public boolean checkObjExist(String bucket, String key) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");

    return _checkObjExist(bucket, key);
  }

  @Override
  public Result<ObjectPutVO> uploadObj(String bucket, String key, String filePath) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    Precondition.checkNotEmpty(filePath, "filePath cannot be empty");
    log.info("upload obj bucket={},key={},filePath={}", bucket, key, filePath);

    Result result = _uploadObj(bucket, key, filePath);
    if (result.isOk()) {
      log.info("upload obj successfully");
    }
    return result;
  }

  @Override
  public Result<ObjectPutVO> uploadObj(String bucket, String key, File file) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    Precondition.checkNotNull(file, "file cannot be empty");
    log.info("upload obj bucket={},key={} by file", bucket, key);

    Result result = _uploadObj(bucket, key, file);
    if (result.isOk()) {
      log.info("upload obj successfully");
    }
    return result;
  }

  @Override
  public Result<ObjectPutVO> uploadObj(String bucket, String key, InputStream inputStream) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    Precondition.checkNotNull(inputStream, "inputStream cannot be empty");
    log.info("upload obj bucket={},key={} by file", bucket, key);

    Result result = _uploadObj(bucket, key, inputStream);
    if (result.isOk()) {
      log.info("upload obj successfully");
    }
    return result;
  }

  @Override
  public Result<ObjectPutVO> uploadObj(ObjectUploadDTO dto) {
    Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
    Precondition.checkNotEmpty(dto.getKey(), "key cannot be empty");
    if (dto.getFile() == null && dto.getInputStream() == null) {
      ExceptionHandler.publishMsg("file, inputStream不能同时为空");
    }
    if (dto.getAclEnum() == null) {
      dto.setAclEnum(AclEnum.PRIVATE);
    }
    log.info("upload obj bucket={},key={} by file", dto.getBucket(), dto.getKey());

    Result result = _uploadObj(dto);
    if (result.isOk()) {
      log.info("upload obj successfully");
    }
    return result;
  }

  @Override
  public Result<String> getObjUrl(ObjectUrlDTO dto) {
    Precondition.checkNotNull(dto);
    Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
    Precondition.checkNotEmpty(dto.getKey(), "key cannot be empty");

    return _getObjUrl(dto);
  }

  @Override
  public Result<String> getObjSignedUrl(String bucket, String key, long expireSeconds) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");

    expireSeconds = expireSeconds < 0 ? 600 /*second*/ : expireSeconds;

    return _getObjSignedUrl(bucket, key, expireSeconds);
  }

  @Override
  public Result<String> getObjSignedUrl(ObjectSignUrlDTO dto) {
    Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
    Precondition.checkNotEmpty(dto.getKey(), "key cannot be empty");

    if (dto.getExpireSeconds() < 0) {
      dto.setExpireSeconds(600);
    }

    return _getObjSignedUrl(dto);
  }

  @Override
  public Result<Boolean> downloadObj(String bucket, String key, String filePath) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    log.info("download obj bucket={},key={},filePath={}", bucket, key, filePath);

    Result result = _downloadObj(bucket, key, filePath);
    if (result.isOk()) {
      log.info("download obj successfully");
    }
    return result;
  }

  @Override
  public Result<Boolean> deleteObj(String bucket, String key) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(key, "key cannot be empty");
    log.info("delete obj bucket={},key={}", bucket, key);

    Result result = _deleteObj(bucket, key);
    if (result.isOk()) {
      log.info("delete obj successfully");
    }
    return result;
  }

  @Override
  public Result<Boolean> deleteObjs(String bucket, List<String> keys) {
    Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
    Precondition.checkNotEmpty(keys, "keys cannot be empty");
    log.info("delete objs bucket={},keys={}", bucket, keys);

    Result result = _deleteObjs(bucket, keys);
    if (result.isOk()) {
      log.info("delete objs successfully");
    }
    return result;
  }

  @Override
  public Result<List<ObjectVO>> queryObjs(ObjectQueryDTO dto) {
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

  public Result _createBucket(String bucket) {
    return Result.failMsg("不支持的操作");
  }

  public Result _createBucket(BucketCreateDTO dto) {
    return Result.failMsg("不支持的操作");
  }

  public Result _deleteBucket(String bucket) {
    return Result.failMsg("不支持的操作");
  }

  public Result<List<BucketVO>> _queryBuckets() {
    return Result.failMsg("不支持的操作");
  }

  public boolean _checkObjExist(String bucket, String key) {
    return false;
  }

  public Result<ObjectPutVO> _uploadObj(String bucket, String key, String filePath) {
    return Result.failMsg("不支持的操作");
  }

  public Result<ObjectPutVO> _uploadObj(String bucket, String key, File file) {
    return Result.failMsg("不支持的操作");
  }

  public Result<ObjectPutVO> _uploadObj(String bucket, String key, InputStream inputStream) {
    return Result.failMsg("不支持的操作");
  }

  public Result<ObjectPutVO> _uploadObj(ObjectUploadDTO dto) {
    return Result.failMsg("不支持的操作");
  }

  public Result<String> _getObjUrl(ObjectUrlDTO dto) {
    Result<String> result = Result.fail();
    String url = "";
    if (dto.isCustomDomainFlag()) {
      url = ossConfig.getEndpoint() + "/" + dto.getKey();
    } else {
      // default url format: http://bucket.xx.com/key
      String bucketUrl = StringUtils.replace(ossConfig.getEndpoint(), "://", "://" + dto.getBucket() + ".");
      url = bucketUrl + "/" + dto.getKey();
    }

    result.value(url);
    return result;
  }

  public Result<String> _getObjSignedUrl(String bucket, String key, long expireSeconds) {
    return Result.failMsg("不支持的操作");
  }

  public Result<String> _getObjSignedUrl(ObjectSignUrlDTO dto) {
    return Result.failMsg("不支持的操作");
  }

  public Result<Boolean> _downloadObj(String bucket, String key, String filePath) {
    return Result.failMsg("不支持的操作");
  }

  public Result<Boolean> _deleteObj(String bucket, String key) {
    return Result.failMsg("不支持的操作");
  }

  public Result<Boolean> _deleteObjs(String bucket, List<String> keys) {
    return Result.failMsg("不支持的操作");
  }

  public Result<List<ObjectVO>> _queryObjs(ObjectQueryDTO dto) {
    return Result.failMsg("不支持的操作");
  }

}
