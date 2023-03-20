package com.github.seaxlab.core.support.oss.manager.impl;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.support.oss.dto.BucketCreateDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectQueryDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectUploadDTO;
import com.github.seaxlab.core.support.oss.dto.OssConfig;
import com.github.seaxlab.core.support.oss.dto.response.BucketRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectRespDTO;
import com.github.seaxlab.core.support.oss.enums.AclEnum;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.support.oss.manager.AbstractOssManager;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.IOUtil;
import com.github.seaxlab.core.util.ListUtil;
import com.obs.services.ObsClient;
import com.obs.services.model.AccessControlList;
import com.obs.services.model.BucketTypeEnum;
import com.obs.services.model.CreateBucketRequest;
import com.obs.services.model.DeleteObjectsRequest;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.ListBucketsRequest;
import com.obs.services.model.ListObjectsRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsBucket;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * module name https://support.huaweicloud.com/sdk-java-devg-obs/obs_21_0404.html
 * https://github.com/huaweicloud/huaweicloud-sdk-java-obs/tree/master/app/src/test/java/samples_java
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Slf4j
public class HuaWeiCloudOssManager extends AbstractOssManager {

  private ObsClient client;

  @Override
  public void _init(OssConfig config) {
    client = new ObsClient(config.getAccessKey(), config.getSecretKey(), config.getEndpoint());
  }

  @Override
  public void _destroy() {
    if (client != null) {
      try {
        client.close();
      } catch (Exception e) {
        log.error("fail to close obs client", e);
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public String getType() {
    return OssTypeEnum.HUAWEI_CLOUD.getCode();
  }

  @Override
  public boolean _checkBucketExist(String bucket) {
    try {
      return client.headBucket(bucket);
    } catch (Exception e) {
      log.error("fail to check bucket exist", e);
    }
    return false;
  }

  @Override
  public void _createBucket(String bucket) {
    client.createBucket(bucket);
  }

  @Override
  public void _createBucket(BucketCreateDTO dto) {
    CreateBucketRequest request = new CreateBucketRequest();
    request.setBucketName(dto.getName());
    request.setAcl(toACL(dto.getAclEnum()));
    client.createBucket(request);
  }

  @Override
  public void _deleteBucket(String bucket) {
    client.deleteBucket(bucket);
  }

  @Override
  public List<BucketRespDTO> _queryBuckets() {
    ListBucketsRequest request = new ListBucketsRequest();
    request.setBucketType(BucketTypeEnum.OBJECT);

    List<ObsBucket> buckets = client.listBuckets(request);
    if (ListUtil.isEmpty(buckets)) {
      log.warn("buckets is empty.");
      return ListUtil.empty();
    }
    List<BucketRespDTO> respDTOs = buckets.stream().map(item -> {
      BucketRespDTO respDTO = new BucketRespDTO();
      respDTO.setName(item.getBucketName());
      return respDTO;
    }).collect(Collectors.toList());

    return respDTOs;
  }

  @Override
  public boolean _checkObjExist(String bucket, String key) {
    return client.doesObjectExist(bucket, key);
  }

  @Override
  public ObjectPutRespDTO _uploadObj(String bucket, String key, String filePath) {
    return uploadObj(bucket, key, new File(filePath));
  }

  @Override
  public ObjectPutRespDTO _uploadObj(String bucket, String key, File file) {
    PutObjectRequest request = new PutObjectRequest();
    request.setBucketName(bucket);
    request.setObjectKey(key);
    request.setFile(file);
    client.putObject(request);

    ObjectPutRespDTO respDTO = new ObjectPutRespDTO();
    respDTO.setKey(key);
    return respDTO;
  }

  @Override
  public ObjectPutRespDTO _uploadObj(String bucket, String key, InputStream inputStream) {
    PutObjectRequest request = new PutObjectRequest();
    request.setBucketName(bucket);
    request.setObjectKey(key);
    request.setInput(inputStream);
    client.putObject(request);

    ObjectPutRespDTO respDTO = new ObjectPutRespDTO();
    respDTO.setKey(key);
    return respDTO;
  }

  @Override
  public ObjectPutRespDTO _uploadObj(ObjectUploadDTO dto) {

    PutObjectRequest request = new PutObjectRequest();
    request.setBucketName(dto.getBucket());
    request.setObjectKey(dto.getKey());
    request.setFile(dto.getFile());
    request.setInput(dto.getInputStream());
    request.setAcl(toACL(dto.getAclEnum()));
    client.putObject(request);

    ObjectPutRespDTO respDTO = new ObjectPutRespDTO();
    respDTO.setKey(dto.getKey());
    return respDTO;
  }


  @Override
  public String _getObjSignedUrl(String bucket, String key, long expireSeconds) {
    TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
    request.setBucketName(bucket);
    request.setObjectKey(key);
    TemporarySignatureResponse response = client.createTemporarySignature(request);
    return response.getSignedUrl();
  }

  @Override
  public String _getObjSignedUrl(ObjectSignUrlDTO dto) {
    TemporarySignatureRequest request = new TemporarySignatureRequest(toHttpMethodEnum(dto.getHttpMethod()),
      dto.getExpireSeconds());
    request.setBucketName(dto.getBucket());
    request.setObjectKey(dto.getKey());
    TemporarySignatureResponse response = client.createTemporarySignature(request);
    return response.getSignedUrl();
  }

  @Override
  public void _downloadObj(String bucket, String key, String filePath) {

    ObsObject obsObject = client.getObject(bucket, key);
    if (obsObject == null) {
      log.warn("obj is not exist");
      ExceptionHandler.publishMsg("对象不存在");
    }
    try {
      InputStream input = obsObject.getObjectContent();
      byte[] b = new byte[1024];
      FileOutputStream fos = new FileOutputStream(filePath);
      int len;
      while ((len = input.read(b)) != -1) {
        fos.write(b, 0, len);
      }
      IOUtil.close(fos);
      IOUtil.close(input);
    } catch (Exception e) {
      log.error("fail to download object from oss", e);
      ExceptionHandler.publishMsg("请求失败");
    }

  }

  @Override
  public void _deleteObj(String bucket, String key) {
    client.deleteObject(bucket, key);
  }

  @Override
  public void _deleteObjs(String bucket, List<String> keys) {
    DeleteObjectsRequest request = new DeleteObjectsRequest();
    request.setBucketName(bucket);
    keys.forEach(key -> request.addKeyAndVersion(key));
    client.deleteObjects(request);
  }

  @Override
  public List<ObjectRespDTO> _queryObjs(ObjectQueryDTO dto) {
    ListObjectsRequest request = new ListObjectsRequest(dto.getBucket());
    request.setMaxKeys(dto.getMaxKeys());
    request.setPrefix(dto.getPrefix());

    ObjectListing data = client.listObjects(request);
    if (data == null) {
      log.warn("data is null");
      return Collections.EMPTY_LIST;
    }

    if (CollectionUtil.isEmpty(data.getObjects())) {
      log.info("objects count is 0");
      return Collections.EMPTY_LIST;

    }

    List<ObjectRespDTO> respDTOs = new ArrayList<>();
    log.info("objects count is {}", data.getObjects().size());

    for (ObsObject obsObject : data.getObjects()) {
      ObjectRespDTO respDTO = new ObjectRespDTO();
      respDTO.setKey(obsObject.getObjectKey());
      respDTOs.add(respDTO);
    }

    return respDTOs;
  }


  //------private
  private HttpMethodEnum toHttpMethodEnum(com.github.seaxlab.core.support.oss.enums.HttpMethodEnum httpMethodEnum) {
    switch (httpMethodEnum) {
      case GET:
        return HttpMethodEnum.GET;
      case PUT:
        return HttpMethodEnum.PUT;
      case POST:
        return HttpMethodEnum.POST;
      case HEAD:
        return HttpMethodEnum.HEAD;
      case DELETE:
        return HttpMethodEnum.DELETE;
      case OPTIONS:
        return HttpMethodEnum.OPTIONS;
      default:
        return HttpMethodEnum.GET;
    }
  }

  public AccessControlList toACL(AclEnum aclEnum) {
    if (aclEnum == null) {
      return AccessControlList.REST_CANNED_PRIVATE;
    }
    switch (aclEnum) {
      case PUBLIC:
        return AccessControlList.REST_CANNED_PUBLIC_READ;
      case PRIVATE:
        return AccessControlList.REST_CANNED_PRIVATE;
    }
    return AccessControlList.REST_CANNED_PRIVATE;
  }
}
