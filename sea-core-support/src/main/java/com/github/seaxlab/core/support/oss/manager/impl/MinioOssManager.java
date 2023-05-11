package com.github.seaxlab.core.support.oss.manager.impl;

import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.support.oss.dto.BucketCreateDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.seaxlab.core.support.oss.dto.response.BucketRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.enums.HttpMethodEnum;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.support.oss.manager.AbstractOssManager;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.ListUtil;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.UploadObjectArgs;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

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

  public MinioOssManager(MinioClient client) {
    this.client = client;
  }

  /**
   * 桶占位符
   */
  private static final String BUCKET_PARAM = "${bucket}";
  /**
   * bucket权限-只读
   */
  private static final String READ_ONLY =
    "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::"
      + BUCKET_PARAM
      + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::"
      + BUCKET_PARAM + "/*\"]}]}";
  /**
   * bucket权限-只写
   */
  private static final String WRITE_ONLY =
    "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::"
      + BUCKET_PARAM
      + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::"
      + BUCKET_PARAM + "/*\"]}]}";
  /**
   * bucket权限-读写
   */
  private static final String READ_WRITE =
    "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::"
      + BUCKET_PARAM
      + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\",\"s3:AbortMultipartUpload\"],\"Resource\":[\"arn:aws:s3:::"
      + BUCKET_PARAM + "/*\"]}]}";

  @Override
  public String getType() {
    return OssTypeEnum.MINIO.getCode();
  }

  @Override
  public boolean _checkBucketExist(String bucket) {
    BucketExistsArgs args = BucketExistsArgs.builder().bucket(bucket).build();
    try {
      return client.bucketExists(args);
    } catch (Exception e) {
      log.error("fail to check bucket exist", e);
    }
    return false;
  }

  @Override
  public void _createBucket(String bucket) {
    MakeBucketArgs args = MakeBucketArgs.builder().bucket(bucket).build();
    try {
      client.makeBucket(args);
    } catch (Exception e) {
      log.error("fail to create bucket", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public void _createBucket(BucketCreateDTO dto) {
    MakeBucketArgs args = MakeBucketArgs.builder().bucket(dto.getName()).build();
    //TODO test
    try {
      client.makeBucket(args);
      SetBucketPolicyArgs.Builder policyArgsBuilder = SetBucketPolicyArgs.builder().bucket(dto.getName());
      switch (dto.getAclEnum()) {
        case PUBLIC:
          policyArgsBuilder.config(READ_ONLY.replace(BUCKET_PARAM, dto.getName()));
          break;
        case PRIVATE:
          policyArgsBuilder.config(WRITE_ONLY.replace(BUCKET_PARAM, dto.getName()));
          break;
      }
      client.setBucketPolicy(policyArgsBuilder.build());
    } catch (Exception e) {
      log.error("fail to create bucket", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public void _deleteBucket(String bucket) {
    RemoveBucketArgs args = RemoveBucketArgs.builder().bucket(bucket).build();
    try {
      client.removeBucket(args);
    } catch (Exception e) {
      log.error("fail to create bucket", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public List<BucketRespDTO> _queryBuckets() {
    try {
      List<Bucket> buckets = client.listBuckets();
      if (ListUtil.isEmpty(buckets)) {
        return ListUtil.empty();
      }

      List<BucketRespDTO> respDTOs = buckets.stream().map(item -> {
        BucketRespDTO respDTO = new BucketRespDTO();
        respDTO.setName(item.name());
        return respDTO;
      }).collect(Collectors.toList());

      return respDTOs;
    } catch (Exception e) {
      log.error("fail to query buckets", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }

    return ListUtil.empty();
  }

  @Override
  public boolean _checkObjExist(String bucket, String key) {

    try {
      GetObjectArgs args = GetObjectArgs.builder().bucket(bucket).object(key).build();

      GetObjectResponse resp = client.getObject(args);
      return resp != null;
    } catch (Exception e) {
      log.error("fail to check obj exist", e);
    }
    return false;
  }

  @Override
  public ObjectPutRespDTO _uploadObj(String bucket, String key, String filePath) {
    ObjectPutRespDTO respDTO = null;
    try {
      UploadObjectArgs args = UploadObjectArgs.builder().bucket(bucket).object(key).filename(filePath).build();
      client.uploadObject(args);

      respDTO = new ObjectPutRespDTO();
      respDTO.setKey(key);
    } catch (Exception e) {
      log.error("fail to upload obj", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
    return respDTO;
  }

  @Override
  public String _getObjSignedUrl(String bucket, String key, long expireSeconds) {

    GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().bucket(bucket).object(key)
      .expiry((int) expireSeconds, TimeUnit.SECONDS).build();
    try {
      return client.getPresignedObjectUrl(args);
    } catch (Exception e) {
      log.error("fail to get obj signed url", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
    return "";
  }

  @Override
  public String _getObjSignedUrl(ObjectSignUrlDTO dto) {
    GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder() //
      .bucket(dto.getBucket()) //
      .object(dto.getKey()) //
      .expiry((int) dto.getExpireSeconds(), TimeUnit.SECONDS)//
      .method(toMethod(dto.getHttpMethod()))//
      .build();
    try {
      return client.getPresignedObjectUrl(args);
    } catch (Exception e) {
      log.error("fail to get obj signed url", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
    return "";
  }

  @Override
  public void _downloadObj(String bucket, String key, String filePath) {
    try {
      GetObjectArgs args = GetObjectArgs.builder().bucket(bucket).object(key).build();
      client.getObject(args);
    } catch (Exception e) {
      log.error("fail to download obj", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public void _deleteObj(String bucket, String key) {
    try {
      RemoveObjectArgs args = RemoveObjectArgs.builder().bucket(bucket).object(key).build();
      client.removeObject(args);
    } catch (Exception e) {
      log.error("fail to delete obj", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public void _deleteObjs(String bucket, List<String> keys) {
    if (CollectionUtil.isEmpty(keys)) {
      log.warn("keys is empty, plz check.");
      return;
    }
    try {
      List<DeleteObject> list = new ArrayList<>();
      keys.forEach(key -> list.add(new DeleteObject(key)));

      RemoveObjectsArgs args = RemoveObjectsArgs.builder().bucket(bucket).objects(list).build();
      client.removeObjects(args);
    } catch (Exception e) {
      log.error("fail to delete objs", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
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
