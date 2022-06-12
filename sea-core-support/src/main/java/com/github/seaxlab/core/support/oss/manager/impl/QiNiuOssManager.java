package com.github.seaxlab.core.support.oss.manager.impl;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.support.oss.dto.*;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.support.oss.manager.AbstractOssManager;
import com.github.seaxlab.core.support.oss.vo.BucketVO;
import com.github.seaxlab.core.support.oss.vo.ObjectPutVO;
import com.github.seaxlab.core.support.oss.vo.ObjectVO;
import com.github.seaxlab.core.util.*;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private Auth auth;
    private Configuration cfg;

    private static final String DEFAULT_REGION = "z0";

    @Override
    public void _init(OssConfig config) {
        auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        //注意这里是华东地区
        cfg = new Configuration(Region.region0());
    }

    @Override
    public void _destroy() {
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
    public Result _createBucket(String bucket) {
        Result result = Result.fail();
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response resp = bucketManager.createBucket(bucket, DEFAULT_REGION);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to create get bucket info", e);
        }
        return result;
    }

    @Override
    public Result _createBucket(BucketCreateDTO dto) {
        //TODO test
        Result result = Result.fail();
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response resp = bucketManager.createBucket(dto.getName(), DEFAULT_REGION);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to create get bucket info", e);
        }
        return result;

    }

    @Override
    public Result _deleteBucket(String bucket) {
        Result result = Result.fail();
        //BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            //TODO
            log.warn("delete bucket is not supported by qiniu oss");
        } catch (Exception e) {
            log.error("fail to create get bucket info", e);
        }
        return result;
    }

    @Override
    public Result<List<BucketVO>> _queryBuckets() {
        Result<List<BucketVO>> result = Result.fail();
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            String[] buckets = bucketManager.buckets();
            if (ArrayUtil.isEmpty(buckets)) {
                result.value(ListUtil.empty());
            } else {
                List<BucketVO> vos = Arrays.stream(buckets).map(item -> {
                    BucketVO vo = new BucketVO();
                    vo.setName(item);
                    return vo;
                }).collect(Collectors.toList());
                result.value(vos);
            }
        } catch (Exception e) {
            log.error("fail to create get bucket info", e);
        }
        return result;
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
    public Result<ObjectPutVO> _uploadObj(String bucket, String key, String filePath) {
        Result<ObjectPutVO> result = Result.fail();

        try {
            String upToken = auth.uploadToken(bucket);
            UploadManager uploadManager = new UploadManager(cfg);
            Response res = uploadManager.put(filePath, key, upToken);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);
            result.value(vo);
        } catch (Exception e) {
            log.error("fail to put obj", e);
        }

        return result;
    }

    @Override
    public Result<ObjectPutVO> _uploadObj(String bucket, String key, File file) {
        Result<ObjectPutVO> result = Result.fail();

        try {
            String upToken = auth.uploadToken(bucket);
            UploadManager uploadManager = new UploadManager(cfg);
            Response res = uploadManager.put(file, key, upToken);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);
            result.value(vo);
        } catch (Exception e) {
            log.error("fail to put obj", e);
        }

        return result;
    }

    @Override
    public Result<ObjectPutVO> _uploadObj(String bucket, String key, InputStream inputStream) {
        Result<ObjectPutVO> result = Result.fail();

        try {
            String filePath = PathUtil.getUserHome() + "/logs/" + IdUtil.shortUUID();
            log.info("try to write temp file[{}]", filePath);
            boolean flag = FileUtil.writeFileFromInputStream(inputStream, filePath);
            if (!flag) {
                log.error("fail to write file");
                result.setMsg("写入临时文件失败");
                return result;
            }

            String upToken = auth.uploadToken(bucket);
            UploadManager uploadManager = new UploadManager(cfg);
            Response res = uploadManager.put(filePath, key, upToken);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);
            result.value(vo);
            FileUtil.deleteFiles(filePath);
        } catch (Exception e) {
            log.error("fail to put obj", e);
        }

        return result;
    }

    @Override
    public Result<ObjectPutVO> _uploadObj(ObjectUploadDTO dto) {
        if (dto.getFile() != null) {
            return _uploadObj(dto.getBucket(), dto.getKey(), dto.getFile());
        } else {
            return _uploadObj(dto.getBucket(), dto.getKey(), dto.getInputStream());
        }
    }

    @Override
    public Result<String> _getObjUrl(ObjectUrlDTO dto) {
        return super._getObjUrl(dto);
    }

    @Override
    public Result<String> _getObjSignedUrl(String bucket, String key, long expireSeconds) {
        Result<String> result = Result.fail();

        try {
            ObjectUrlDTO urlDTO = new ObjectUrlDTO();
            urlDTO.setBucket(bucket);
            urlDTO.setKey(key);
            urlDTO.setCustomDomainFlag(true);
            String url = auth.privateDownloadUrl(getObjUrl(urlDTO).getData(), expireSeconds);
            result.value(url);
        } catch (Exception e) {
            log.error("fail to get obj signed url", e);
        }

        return result;
    }

    @Override
    public Result<String> _getObjSignedUrl(ObjectSignUrlDTO dto) {
        Result<String> result = Result.fail();

        try {
            ObjectUrlDTO urlDTO = new ObjectUrlDTO();
            urlDTO.setBucket(dto.getBucket());
            urlDTO.setKey(dto.getKey());
            urlDTO.setCustomDomainFlag(true);
            String url = auth.privateDownloadUrl(getObjUrl(urlDTO).getData(), dto.getExpireSeconds());
            result.value(url);
        } catch (Exception e) {
            log.error("fail to get obj signed url", e);
        }

        return result;
    }

    @Override
    public Result<Boolean> _downloadObj(String bucket, String key, String filePath) {
        Result<Boolean> result = Result.fail();

        ObjectUrlDTO urlDTO = new ObjectUrlDTO();
        urlDTO.setBucket(bucket);
        urlDTO.setKey(key);
        urlDTO.setCustomDomainFlag(true);
        String url = getObjUrl(urlDTO).getData();

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
            result.value(true);
        } catch (IOException e) {
            log.error("fail to download obj", e);
        }
        return result;
    }

    @Override
    public Result<Boolean> _deleteObj(String bucket, String key) {
        Result<Boolean> result = Result.fail();

        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to delete obj", e);
        }

        return result;
    }

    @Override
    public Result<Boolean> _deleteObjs(String bucket, List<String> keys) {
        Result<Boolean> result = Result.fail();

        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {

            BucketManager.BatchOperations operations = new BucketManager.BatchOperations();
            bucketManager.batch(operations.addDeleteOp(bucket, ArrayUtil.toArray(keys, String.class)));
            //for (String key : keys) {
            //    bucketManager.delete(bucket, key);
            //}
            result.value(true);
        } catch (Exception e) {
            log.error("fail to delete objs", e);
        }

        return result;
    }

    @Override
    public Result<List<ObjectVO>> _queryObjs(ObjectQueryDTO dto) {
        Result<List<ObjectVO>> result = Result.fail();
        BucketManager bucketManager = new BucketManager(auth, cfg);

        try {
            FileListing fileListing = bucketManager.listFiles(dto.getBucket(), dto.getPrefix(), null, dto.getMaxKeys(), null);
            FileInfo[] items = fileListing.items;
            List<ObjectVO> vos = Arrays.stream(items)
                                       .map(item -> {
                                           ObjectVO vo = new ObjectVO();
                                           vo.setKey(item.key);
                                           return vo;
                                       }).collect(Collectors.toList());
            result.value(vos);
        } catch (Exception e) {
            log.error("fail to query objs", e);
        }

        return result;
    }
}
