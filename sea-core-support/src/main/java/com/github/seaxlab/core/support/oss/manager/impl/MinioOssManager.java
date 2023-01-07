package com.github.seaxlab.core.support.oss.manager.impl;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.support.oss.dto.BucketCreateDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.seaxlab.core.support.oss.dto.OssConfig;
import com.github.seaxlab.core.support.oss.dto.response.BucketRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.enums.HttpMethodEnum;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.support.oss.manager.AbstractOssManager;
import com.github.seaxlab.core.util.ListUtil;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * minio oss manager
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
@Slf4j
public class MinioOssManager extends AbstractOssManager {

  private MinioClient client;

  /**
   * 桶占位符
   */
  private static final String BUCKET_PARAM = "${bucket}";
  /**
   * bucket权限-只读
   */
  private static final String READ_ONLY = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";
  /**
   * bucket权限-只写
   */
  private static final String WRITE_ONLY = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";
  /**
   * bucket权限-读写
   */
  private static final String READ_WRITE = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\",\"s3:AbortMultipartUpload\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";

  @Override
  public void _init(OssConfig config) {
    client = MinioClient.builder()
                        .endpoint(config.getEndpoint())
                        .credentials(config.getAccessKey(), config.getSecretKey())
                        .build();
  }

  @Override
  public void _destroy() {
    client = null;
  }

  @Override
  public String getType() {
    return OssTypeEnum.MINIO.getCode();
  }

  @Override
  public boolean _checkBucketExist(String bucket) {
    BucketExistsArgs args = BucketExistsArgs.builder()
                                            .bucket(bucket)
                                            .build();
    try {
      return client.bucketExists(args);
    } catch (Exception e) {
      log.error("fail to check bucket exist", e);
    }
    return false;
  }

  @Override
  public Result _createBucket(String bucket) {
    Result result = Result.fail();
    MakeBucketArgs args = MakeBucketArgs.builder()
                                        .bucket(bucket)
                                        .build();
    try {
      client.makeBucket(args);
      result.value(true);
    } catch (Exception e) {
      log.error("fail to create bucket", e);
    }
    return result;
  }

  @Override
  public Result _createBucket(BucketCreateDTO dto) {
    Result result = Result.fail();
    MakeBucketArgs args = MakeBucketArgs.builder()
                                        .bucket(dto.getName())
                                        .build();
    //TODO test
    try {
      client.makeBucket(args);
      SetBucketPolicyArgs.Builder policyArgsBuilder = SetBucketPolicyArgs.builder()
                                                                         .bucket(dto.getName());
      switch (dto.getAclEnum()) {
        case PUBLIC:
          policyArgsBuilder.config(READ_ONLY.replace(BUCKET_PARAM, dto.getName()));
          break;
        case PRIVATE:
          policyArgsBuilder.config(WRITE_ONLY.replace(BUCKET_PARAM, dto.getName()));
          break;
      }
      client.setBucketPolicy(policyArgsBuilder.build());
      result.value(true);
    } catch (Exception e) {
      log.error("fail to create bucket", e);
    }
    return result;
  }

  @Override
  public Result _deleteBucket(String bucket) {
    Result result = Result.fail();
    RemoveBucketArgs args = RemoveBucketArgs.builder()
                                            .bucket(bucket)
                                            .build();
    try {
      client.removeBucket(args);
      result.value(true);
    } catch (Exception e) {
      log.error("fail to create bucket", e);
    }
    return result;

  }

  @Override
  public Result<List<BucketRespDTO>> _queryBuckets() {
    Result<List<BucketRespDTO>> result = Result.fail();

    try {
      List<Bucket> buckets = client.listBuckets();
      if (ListUtil.isEmpty(buckets)) {
        result.value(ListUtil.empty());
        return result;
      }

      List<BucketRespDTO> vos = buckets.stream()
                                       .map(item -> {
                                         BucketRespDTO vo = new BucketRespDTO();
                                         vo.setName(item.name());
                                         return vo;
                                       }).collect(Collectors.toList());
      result.value(vos);
    } catch (Exception e) {
      log.error("fail to query buckets", e);
    }
    return result;
  }

  @Override
  public boolean _checkObjExist(String bucket, String key) {

    try {
      GetObjectArgs args = GetObjectArgs.builder()
                                        .bucket(bucket)
                                        .object(key)
                                        .build();

      GetObjectResponse resp = client.getObject(args);
      return resp != null;
    } catch (Exception e) {
      log.error("fail to check obj exist", e);
    }
    return false;
  }

  @Override
  public Result<ObjectPutRespDTO> _uploadObj(String bucket, String key, String filePath) {
    Result<ObjectPutRespDTO> result = Result.fail();
    try {
      UploadObjectArgs args = UploadObjectArgs.builder()
                                              .bucket(bucket)
                                              .object(key)
                                              .filename(filePath)
                                              .build();
      client.uploadObject(args);

      ObjectPutRespDTO vo = new ObjectPutRespDTO();
      vo.setKey(key);

      result.value(vo);
    } catch (Exception e) {
      log.error("fail to upload obj", e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  @Override
  public Result<String> _getObjSignedUrl(String bucket, String key, long expireSeconds) {
    Result<String> result = Result.fail();

    GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                                                              .bucket(bucket)
                                                              .object(key)
                                                              .expiry((int) expireSeconds, TimeUnit.SECONDS)
                                                              .build();
    try {
      result.value(client.getPresignedObjectUrl(args));
    } catch (Exception e) {
      log.error("fail to get obj signed url", e);
      result.setMsg("获取签名数据失败");
    }
    return result;
  }

  @Override
  public Result<String> _getObjSignedUrl(ObjectSignUrlDTO dto) {
    Result<String> result = Result.fail();

    GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                                                              .bucket(dto.getBucket())
                                                              .object(dto.getKey())
                                                              .expiry((int) dto.getExpireSeconds(), TimeUnit.SECONDS)
                                                              .method(toMethod(dto.getHttpMethod()))
                                                              .build();
    try {
      result.value(client.getPresignedObjectUrl(args));
    } catch (Exception e) {
      log.error("fail to get obj signed url", e);
      result.setMsg("获取签名数据失败");
    }
    return result;
  }

  @Override
  public Result<Boolean> _downloadObj(String bucket, String key, String filePath) {
    Result<Boolean> result = Result.fail();
    try {
      GetObjectArgs args = GetObjectArgs.builder()
                                        .bucket(bucket)
                                        .object(key)
                                        .build();
      GetObjectResponse response = client.getObject(args);

      result.value(true);
    } catch (Exception e) {
      log.error("fail to download obj", e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  @Override
  public Result<Boolean> _deleteObj(String bucket, String key) {
    Result<Boolean> result = Result.fail();
    try {
      RemoveObjectArgs args = RemoveObjectArgs.builder()
                                              .bucket(bucket)
                                              .object(key)
                                              .build();
      client.removeObject(args);
      result.setData(true);
    } catch (Exception e) {
      log.error("fail to delete obj", e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  @Override
  public Result<Boolean> _deleteObjs(String bucket, List<String> keys) {
    Result<Boolean> result = Result.fail();

    try {
      List<DeleteObject> list = new ArrayList<>();
      keys.forEach(key -> list.add(new DeleteObject(key)));

      if (list.isEmpty()) {
        result.setMsg("keys is empty.");
        return result;
      }

      RemoveObjectsArgs args = RemoveObjectsArgs.builder()
                                                .bucket(bucket)
                                                .objects(list)
                                                .build();
      client.removeObjects(args);
      result.setData(true);
    } catch (Exception e) {
      log.error("fail to delete objs", e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  // --------------------private
  private Method toMethod(HttpMethodEnum httpMethod) {
    switch (httpMethod) {
      case GET:
        return Method.GET;
      case POST:
        return Method.POST;
      case DELETE:
        return Method.DELETE;
      case PUT:
        return Method.PUT;
      case HEAD:
        return Method.HEAD;
      case OPTIONS:
        log.warn(" options is not supported by minio, we will use GET instead.");
        //重点：不支持的方法
        return Method.GET;
      default:
        log.warn("input http method is null, so we will use GET instead.");
        return Method.GET;
    }
  }
}
