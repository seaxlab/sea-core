package com.github.seaxlab.core.support.oss.manager.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.support.oss.dto.BucketCreateDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectQueryDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectUploadDTO;
import com.github.seaxlab.core.support.oss.dto.OssConfig;
import com.github.seaxlab.core.support.oss.dto.response.BucketRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectRespDTO;
import com.github.seaxlab.core.support.oss.enums.AclEnum;
import com.github.seaxlab.core.support.oss.enums.HttpMethodEnum;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.support.oss.manager.AbstractOssManager;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.ListUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

/**
 * aliyun oss manager
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
@Slf4j
public class AliyunOssManager extends AbstractOssManager {

  private OSSClient client;

  @Override
  public void _init(OssConfig config) {
    CredentialsProvider credentialsProvider = new DefaultCredentialProvider(config.getAccessKey(),
      config.getSecretKey());
    client = new OSSClient(config.getEndpoint(), credentialsProvider, null);
  }

  @Override
  public void _destroy() {
    try {
      if (client != null) {
        client.shutdown();
      }
      client = null;
    } catch (Exception e) {
      log.error("fail to destroy oss client", e);
    }
  }

  @Override
  public String getType() {
    return OssTypeEnum.ALI_YUN.getCode();
  }

  public boolean _checkBucketExist(String bucket) {
    return client.doesBucketExist(bucket);
  }

  public void _createBucket(String bucket) {
    try {
      client.createBucket(bucket);
    } catch (Exception e) {
      log.error("fail to create bucket", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public void _createBucket(BucketCreateDTO dto) {
    try {
      CreateBucketRequest request = new CreateBucketRequest(dto.getName());
      request.setCannedACL(toACL(dto.getAclEnum()));
      client.createBucket(request);
    } catch (Exception e) {
      log.error("fail to create bucket", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  public void _deleteBucket(String bucket) {
    try {
      client.deleteBucket(bucket);
    } catch (Exception e) {
      log.error("fail to delete bucket", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  public List<BucketRespDTO> _queryBuckets() {
    List<Bucket> buckets = client.listBuckets();
    if (ListUtil.isEmpty(buckets)) {
      return ListUtil.empty();
    }
    List<BucketRespDTO> respDTOS = buckets.stream().map(item -> {
      BucketRespDTO respDTO = new BucketRespDTO();
      respDTO.setName(item.getName());
      return respDTO;
    }).collect(Collectors.toList());

    return respDTOS;
  }

  public boolean _checkObjExist(String bucket, String key) {
    return client.doesObjectExist(bucket, key);
  }

  public ObjectPutRespDTO _uploadObj(String bucket, String key, String filePath) {

    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(filePath);
    } catch (FileNotFoundException e) {
      log.warn("fail not found", e);
    }
    PutObjectResult ret = client.putObject(bucket, key, inputStream);

    ObjectPutRespDTO respDTO = new ObjectPutRespDTO();
    respDTO.setKey(key);
    return respDTO;
  }


  public ObjectPutRespDTO _uploadObj(String bucket, String key, File file) {

    PutObjectRequest request = new PutObjectRequest(bucket, key, file);
    client.putObject(request);

    ObjectPutRespDTO respDTO = new ObjectPutRespDTO();
    respDTO.setKey(key);

    return respDTO;
  }

  public ObjectPutRespDTO _uploadObj(String bucket, String key, InputStream inputStream) {
    PutObjectRequest request = new PutObjectRequest(bucket, key, inputStream);
    client.putObject(request);

    ObjectPutRespDTO respDTO = new ObjectPutRespDTO();
    respDTO.setKey(key);

    return respDTO;
  }

  @Override
  public ObjectPutRespDTO _uploadObj(ObjectUploadDTO dto) {

    PutObjectRequest request = null;
    if (dto.getFile() != null) {
      request = new PutObjectRequest(dto.getBucket(), dto.getKey(), dto.getFile());
    } else {
      request = new PutObjectRequest(dto.getBucket(), dto.getKey(), dto.getInputStream());
    }
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setObjectAcl(toACL(dto.getAclEnum()));
    request.setMetadata(metadata);

    client.putObject(request);

    ObjectPutRespDTO respDTO = new ObjectPutRespDTO();
    respDTO.setKey(dto.getKey());

    return respDTO;
  }

  public String _getObjSignedUrl(String bucket, String key, long expireSeconds) {
    Date now = new Date();
    Date expireDate = DateUtils.addSeconds(now, (int) expireSeconds);
    URL url = client.generatePresignedUrl(bucket, key, expireDate, HttpMethod.GET);
    return url.toString();
  }

  public String _getObjSignedUrl(ObjectSignUrlDTO dto) {
    Date now = new Date();
    Date expireDate = DateUtils.addSeconds(now, (int) dto.getExpireSeconds());
    URL url = client.generatePresignedUrl(dto.getBucket(), dto.getKey(), expireDate,
      toHttpMethodEnum(dto.getHttpMethod()));
    return url.toString();
  }

  public void _downloadObj(String bucket, String key, String newFilePath) {
    OSSObject object = client.getObject(bucket, key);
    ExceptionHandler.publishMsg("对象不存在");
    try {
      InputStream input = object.getObjectContent();
      byte[] b = new byte[1024];
      FileOutputStream fos = new FileOutputStream(newFilePath);
      int len;
      while ((len = input.read(b)) != -1) {
        fos.write(b, 0, len);
      }
      fos.close();
      input.close();
    } catch (IOException e) {
      log.error("io io exception", e);
      ExceptionHandler.publishMsg("请求异常");
    }
  }

  public void _deleteObj(String bucket, String key) {
    client.deleteObject(bucket, key);
  }

  public void _deleteObjs(String bucket, List<String> keys) {
    Precondition.checkNotNull(bucket);

    DeleteObjectsRequest request = new DeleteObjectsRequest(bucket);
    request.setKeys(keys);
    client.deleteObjects(request);
  }

  public List<ObjectRespDTO> _queryObjs(ObjectQueryDTO dto) {
    Precondition.checkNotNull(dto.getBucket());

    ListObjectsRequest request = new ListObjectsRequest(dto.getBucket());
    request.setMaxKeys(dto.getMaxKeys());
    request.setPrefix(dto.getPrefix());

    ObjectListing data = client.listObjects(request);
    if (data == null) {
      log.warn("data is null");
      return Collections.EMPTY_LIST;
    }

    if (CollectionUtil.isEmpty(data.getObjectSummaries())) {
      log.info("objects count is 0");
      return Collections.EMPTY_LIST;
    }

    List<ObjectRespDTO> respDTOS = new ArrayList<>();
    log.info("objects count is {}", data.getObjectSummaries().size());

    for (OSSObjectSummary objectSummary : data.getObjectSummaries()) {

      ObjectRespDTO respDTO = new ObjectRespDTO();
      respDTO.setKey(objectSummary.getKey());
      respDTOS.add(respDTO);
    }

    return respDTOS;
  }


  // ---------------private
  private HttpMethod toHttpMethodEnum(HttpMethodEnum httpMethodEnum) {
    switch (httpMethodEnum) {
      case GET:
        return HttpMethod.GET;
      case PUT:
        return HttpMethod.PUT;
      case POST:
        return HttpMethod.POST;
      case HEAD:
        return HttpMethod.HEAD;
      case DELETE:
        return HttpMethod.DELETE;
      case OPTIONS:
        return HttpMethod.OPTIONS;
      default:
        return HttpMethod.GET;
    }
  }

  public CannedAccessControlList toACL(AclEnum aclEnum) {
    if (aclEnum == null) {
      return CannedAccessControlList.Private;
    }
    switch (aclEnum) {
      case PUBLIC:
        return CannedAccessControlList.PublicRead;
      case PRIVATE:
        return CannedAccessControlList.Private;
    }
    return CannedAccessControlList.Private;
  }
}
