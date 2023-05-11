package com.github.seaxlab.core.support.oss.manager;

import com.github.seaxlab.core.support.oss.dto.BucketCreateDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectQueryDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectUploadDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectUrlDTO;
import com.github.seaxlab.core.support.oss.dto.response.BucketRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectRespDTO;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * oss manager
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
public interface OssManager {

  /**
   * get current oss type
   *
   * @return oss type
   */
  String getType();

  /**
   * 校验bucket是否存在
   *
   * @param bucket
   * @return boolean
   */
  boolean checkBucketExist(String bucket);

  /**
   * 创建bucket，默认是私有的
   *
   * @param bucket
   */
  void createBucket(String bucket);


  /**
   * create bucket
   *
   * @param dto
   */
  void createBucket(BucketCreateDTO dto);

  /**
   * 删除bucket
   *
   * @param bucket
   */
  void deleteBucket(String bucket);

  /**
   * 查询所有bucket
   *
   * @return
   */
  List<BucketRespDTO> queryBuckets();

  /**
   * check obj exist
   *
   * @param bucket
   * @param key
   * @return boolean
   */
  boolean checkObjExist(String bucket, String key);

  /**
   * upload obj by file path
   *
   * @param bucket
   * @param key
   * @param filePath
   * @return ObjectPutRespDTO
   */
  ObjectPutRespDTO uploadObj(String bucket, String key, String filePath);

  /**
   * upload obj by file
   *
   * @param bucket
   * @param key
   * @param file
   * @return ObjectPutRespDTO
   */
  ObjectPutRespDTO uploadObj(String bucket, String key, File file);

  /**
   * upload obj by stream
   *
   * @param bucket
   * @param key
   * @param inputStream
   * @return ObjectPutRespDTO
   */
  ObjectPutRespDTO uploadObj(String bucket, String key, InputStream inputStream);

  /**
   * update obj
   * <p>
   * 私有桶中公开可读文件
   * </p>
   *
   * @param dto
   * @return ObjectPutRespDTO
   */
  ObjectPutRespDTO uploadObj(ObjectUploadDTO dto);

  /**
   * get object url.
   *
   * @param dto
   * @return
   */
  String getObjUrl(ObjectUrlDTO dto);

  /**
   * get obj signed url
   *
   * @param bucket
   * @param key
   * @param expireSeconds
   * @return
   */
  String getObjSignedUrl(String bucket, String key, long expireSeconds);

  /**
   * get obj signed url
   *
   * @param dto
   * @return
   */
  String getObjSignedUrl(ObjectSignUrlDTO dto);

  /**
   * 下载文件
   *
   * @param bucket
   * @param key
   * @param filePath 文件路径
   */
  void downloadObj(String bucket, String key, String filePath);

  /**
   * 删除对象
   *
   * @param bucket
   * @param key
   */
  void deleteObj(String bucket, String key);

  /**
   * 批量删除对象
   *
   * @param bucket
   * @param keys
   */
  void deleteObjs(String bucket, List<String> keys);

  /**
   * query objs
   *
   * @param dto
   * @return
   */
  List<ObjectRespDTO> queryObjs(ObjectQueryDTO dto);
}
