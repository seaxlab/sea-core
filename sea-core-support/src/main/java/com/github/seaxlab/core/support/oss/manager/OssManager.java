package com.github.seaxlab.core.support.oss.manager;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.support.oss.dto.*;
import com.github.seaxlab.core.support.oss.vo.BucketVO;
import com.github.seaxlab.core.support.oss.vo.ObjectPutVO;
import com.github.seaxlab.core.support.oss.vo.ObjectVO;

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
public interface OssManager {

  /**
   * init
   *
   * @param config
   * @return
   */
  void init(OssConfig config);

  /**
   * destroy
   *
   * @return
   */
  void destroy();

  /**
   * return current oss type
   *
   * @return
   */
  String getType();

  /**
   * 校验bucket是否存在
   *
   * @param bucket
   * @return
   */
  boolean checkBucketExist(String bucket);

  /**
   * 创建bucket，默认是私有的
   *
   * @param bucket
   * @return
   */
  Result createBucket(String bucket);


  /**
   * create bucket
   *
   * @param dto
   * @return
   */
  Result<Boolean> createBucket(BucketCreateDTO dto);

  /**
   * 删除bucket
   *
   * @param bucket
   * @return
   */
  Result deleteBucket(String bucket);

  /**
   * 查询所有bucket
   *
   * @return
   */
  Result<List<BucketVO>> queryBuckets();

  /**
   * check obj exist
   *
   * @param bucket
   * @param key
   * @return
   */
  boolean checkObjExist(String bucket, String key);

  /**
   * upload obj by file path
   *
   * @param bucket
   * @param key
   * @param filePath
   * @return
   */
  Result<ObjectPutVO> uploadObj(String bucket, String key, String filePath);

  /**
   * upload obj by file
   *
   * @param bucket
   * @param key
   * @param file
   * @return
   */
  Result<ObjectPutVO> uploadObj(String bucket, String key, File file);

  /**
   * upload obj by stream
   *
   * @param bucket
   * @param key
   * @param inputStream
   * @return
   */
  Result<ObjectPutVO> uploadObj(String bucket, String key, InputStream inputStream);

  /**
   * update obj
   * <p>
   * 私有桶中公开可读文件
   * </p>
   *
   * @param dto
   * @return
   */
  Result<ObjectPutVO> uploadObj(ObjectUploadDTO dto);

  /**
   * get object url.
   *
   * @param dto
   * @return
   */
  Result<String> getObjUrl(ObjectUrlDTO dto);

  /**
   * get obj signed url
   *
   * @param bucket
   * @param key
   * @param expireSeconds
   * @return
   */
  Result<String> getObjSignedUrl(String bucket, String key, long expireSeconds);

  /**
   * get obj signed url
   *
   * @param dto
   * @return
   */
  Result<String> getObjSignedUrl(ObjectSignUrlDTO dto);

  /**
   * 下载文件
   *
   * @param bucket
   * @param key
   * @param filePath 文件路径
   * @return
   */
  Result<Boolean> downloadObj(String bucket, String key, String filePath);

  /**
   * 删除对象
   *
   * @param bucket
   * @param key
   * @return
   */
  Result<Boolean> deleteObj(String bucket, String key);

  /**
   * 批量删除对象
   *
   * @param bucket
   * @param keys
   * @return
   */
  Result<Boolean> deleteObjs(String bucket, List<String> keys);

  /**
   * query objs
   *
   * @param dto
   * @return
   */
  Result<List<ObjectVO>> queryObjs(ObjectQueryDTO dto);
}
