package com.github.seaxlab.core.support.oss.manager.impl;

import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.support.oss.dto.BucketCreateDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectQueryDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectUploadDTO;
import com.github.seaxlab.core.support.oss.dto.ObjectUrlDTO;
import com.github.seaxlab.core.support.oss.dto.response.BucketRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectPutRespDTO;
import com.github.seaxlab.core.support.oss.dto.response.ObjectRespDTO;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.support.oss.manager.AbstractOssManager;
import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.FileUtil;
import com.github.seaxlab.core.util.IOUtil;
import com.github.seaxlab.core.util.IdUtil;
import com.github.seaxlab.core.util.ListUtil;
import com.github.seaxlab.core.util.PathUtil;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * qiniu oss manager
 * <p>
 * <p></p>https://developer.qiniu.com/kodo/1239/java#5</p>
 * <p>
 * https://github.com/qiniu/java-sdk/blob/master/examples/BatchDemo.java
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
@Slf4j
public class QiNiuOssManager extends AbstractOssManager {

  private final Auth auth;
  private final Configuration cfg;

  private static final String DEFAULT_REGION = "z0";

  public QiNiuOssManager(Auth auth, Configuration cfg) {
    this.auth = auth;
    this.cfg = cfg;
  }


  @Override
  public String getType() {
    return OssTypeEnum.QINIU_CLOUD.getCode();
  }

  @Override
  public boolean _checkBucketExist(String bucket) {
    BucketManager bucketManager = new BucketManager(auth, cfg);
    try {
      String[] buckets = bucketManager.buckets();
      return EqualUtil.isIn(bucket, buckets);
    } catch (Exception e) {
      log.error("fail to query get bucket info", e);
    }
    return false;
  }

  @Override
  public void _createBucket(String bucket) {
    BucketManager bucketManager = new BucketManager(auth, cfg);
    try {
      Response resp = bucketManager.createBucket(bucket, DEFAULT_REGION);
    } catch (Exception e) {
      log.error("fail to create get bucket info", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public void _createBucket(BucketCreateDTO dto) {
    BucketManager bucketManager = new BucketManager(auth, cfg);
    try {
      Response resp = bucketManager.createBucket(dto.getName(), DEFAULT_REGION);
    } catch (Exception e) {
      log.error("fail to create get bucket info", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public void _deleteBucket(String bucket) {
    //BucketManager bucketManager = new BucketManager(auth, cfg);
    try {
      //TODO
      log.warn("delete bucket is not supported by qiniu oss");
    } catch (Exception e) {
      log.error("fail to create get bucket info", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public List<BucketRespDTO> _queryBuckets() {
    BucketManager bucketManager = new BucketManager(auth, cfg);
    try {
      String[] buckets = bucketManager.buckets();
      if (ArrayUtil.isEmpty(buckets)) {
        return ListUtil.empty();
      }
      List<BucketRespDTO> respDTOs = Arrays.stream(buckets).map(item -> {
        BucketRespDTO respDTO = new BucketRespDTO();
        respDTO.setName(item);
        return respDTO;
      }).collect(Collectors.toList());
      return respDTOs;
    } catch (Exception e) {
      log.error("fail to create get bucket info", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }

    return ListUtil.empty();
  }

  @Override
  public boolean _checkObjExist(String bucket, String key) {
    BucketManager bucketManager = new BucketManager(auth, cfg);
    try {
      FileInfo fileInfo = bucketManager.stat(bucket, key);
      return fileInfo != null;
    } catch (Exception e) {
      log.error("fail to create get bucket info", e);
    }
    return false;
  }

  @Override
  public ObjectPutRespDTO _uploadObj(String bucket, String key, String filePath) {
    ObjectPutRespDTO respDTO = null;
    try {
      String upToken = auth.uploadToken(bucket);
      UploadManager uploadManager = new UploadManager(cfg);
      Response res = uploadManager.put(filePath, key, upToken);

      respDTO = new ObjectPutRespDTO();
      respDTO.setKey(key);
    } catch (Exception e) {
      log.error("fail to put obj", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
    return respDTO;
  }

  @Override
  public ObjectPutRespDTO _uploadObj(String bucket, String key, File file) {
    ObjectPutRespDTO respDTO = null;
    try {
      String upToken = auth.uploadToken(bucket);
      UploadManager uploadManager = new UploadManager(cfg);
      Response res = uploadManager.put(file, key, upToken);

      respDTO = new ObjectPutRespDTO();
      respDTO.setKey(key);
    } catch (Exception e) {
      log.error("fail to put obj", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }

    return respDTO;
  }

  @Override
  public ObjectPutRespDTO _uploadObj(String bucket, String key, InputStream inputStream) {
    ObjectPutRespDTO respDTO = null;
    try {
      String filePath = PathUtil.getUserHome() + "/logs/" + IdUtil.shortUUID();
      log.info("try to write temp file[{}]", filePath);
      boolean flag = FileUtil.writeFileFromInputStream(inputStream, filePath);
      if (!flag) {
        log.error("fail to write file");
        ExceptionHandler.publishMsg("写入临时文件失败");
        return respDTO;
      }

      String upToken = auth.uploadToken(bucket);
      UploadManager uploadManager = new UploadManager(cfg);
      Response res = uploadManager.put(filePath, key, upToken);

      respDTO = new ObjectPutRespDTO();
      respDTO.setKey(key);
      FileUtil.deleteFiles(filePath);
    } catch (Exception e) {
      log.error("fail to put obj", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }

    return respDTO;
  }

  @Override
  public ObjectPutRespDTO _uploadObj(ObjectUploadDTO dto) {
    if (dto.getFile() != null) {
      return _uploadObj(dto.getBucket(), dto.getKey(), dto.getFile());
    } else {
      return _uploadObj(dto.getBucket(), dto.getKey(), dto.getInputStream());
    }
  }

  @Override
  public String _getObjUrl(ObjectUrlDTO dto) {
    return super._getObjUrl(dto);
  }

  @Override
  public String _getObjSignedUrl(String bucket, String key, long expireSeconds) {
    try {
      ObjectUrlDTO urlDTO = new ObjectUrlDTO();
      urlDTO.setBucket(bucket);
      urlDTO.setKey(key);
      urlDTO.setCustomDomainFlag(true);
      return auth.privateDownloadUrl(getObjUrl(urlDTO), expireSeconds);
    } catch (Exception e) {
      log.error("fail to get obj signed url", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }

    return "";
  }

  @Override
  public String _getObjSignedUrl(ObjectSignUrlDTO dto) {

    try {
      ObjectUrlDTO urlDTO = new ObjectUrlDTO();
      urlDTO.setBucket(dto.getBucket());
      urlDTO.setKey(dto.getKey());
      urlDTO.setCustomDomainFlag(true);
      return auth.privateDownloadUrl(getObjUrl(urlDTO), dto.getExpireSeconds());
    } catch (Exception e) {
      log.error("fail to get obj signed url", e);
    }
    return "";
  }

  @Override
  public void _downloadObj(String bucket, String key, String filePath) {
    ObjectUrlDTO urlDTO = new ObjectUrlDTO();
    urlDTO.setBucket(bucket);
    urlDTO.setKey(key);
    urlDTO.setCustomDomainFlag(true);
    String url = getObjUrl(urlDTO);

    OkHttpClient client = new OkHttpClient();
    Request req = new Request.Builder().url(url).build();
    okhttp3.Response resp = null;
    try {
      resp = client.newCall(req).execute();
      if (resp.isSuccessful()) {
        ResponseBody body = resp.body();
        InputStream input = body.byteStream();
        byte[] b = new byte[1024];
        FileOutputStream fos = new FileOutputStream(filePath);
        int len;
        while ((len = input.read(b)) != -1) {
          fos.write(b, 0, len);
        }
        IOUtil.close(fos);
        IOUtil.close(input);
      } else {
        log.warn("get file error");
      }
    } catch (IOException e) {
      log.error("fail to download obj", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public void _deleteObj(String bucket, String key) {
    BucketManager bucketManager = new BucketManager(auth, cfg);
    try {
      bucketManager.delete(bucket, key);
    } catch (Exception e) {
      log.error("fail to delete obj", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }

  }

  @Override
  public void _deleteObjs(String bucket, List<String> keys) {
    BucketManager bucketManager = new BucketManager(auth, cfg);
    try {

      BucketManager.BatchOperations operations = new BucketManager.BatchOperations();
      bucketManager.batch(operations.addDeleteOp(bucket, ArrayUtil.toArray(keys, String.class)));
      //for (String key : keys) {
      //    bucketManager.delete(bucket, key);
      //}
    } catch (Exception e) {
      log.error("fail to delete objs", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REQUEST_INVALID);
    }
  }

  @Override
  public List<ObjectRespDTO> _queryObjs(ObjectQueryDTO dto) {
    BucketManager bucketManager = new BucketManager(auth, cfg);

    try {
      FileListing fileListing = bucketManager.listFiles(dto.getBucket(), dto.getPrefix(), null, dto.getMaxKeys(), null);
      FileInfo[] items = fileListing.items;
      List<ObjectRespDTO> respDTOs = Arrays.stream(items).map(item -> {
        ObjectRespDTO respDTO = new ObjectRespDTO();
        respDTO.setKey(item.key);
        return respDTO;
      }).collect(Collectors.toList());
      return respDTOs;
    } catch (Exception e) {
      log.error("fail to query objs", e);
    }

    return ListUtil.empty();
  }
}
