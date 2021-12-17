package com.github.spy.sea.core.support.oss.manager.impl;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.BucketCreateDTO;
import com.github.spy.sea.core.support.oss.dto.ObjectQueryDTO;
import com.github.spy.sea.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.enums.AclEnum;
import com.github.spy.sea.core.support.oss.enums.OssTypeEnum;
import com.github.spy.sea.core.support.oss.manager.AbstractOssManager;
import com.github.spy.sea.core.support.oss.vo.BucketVO;
import com.github.spy.sea.core.support.oss.vo.ObjectPutVO;
import com.github.spy.sea.core.support.oss.vo.ObjectVO;
import com.github.spy.sea.core.util.CollectionUtil;
import com.github.spy.sea.core.util.IOUtil;
import com.github.spy.sea.core.util.ListUtil;
import com.obs.services.ObsClient;
import com.obs.services.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * module name
 * https://support.huaweicloud.com/sdk-java-devg-obs/obs_21_0404.html
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
    public BaseResult<Boolean> _createBucket(String bucket) {
        BaseResult<Boolean> result = BaseResult.fail();

        try {
            client.createBucket(bucket);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to create bucket", e);
            result.setErrorMessage("创建桶失败");
        }
        return result;
    }

    @Override
    public BaseResult _createBucket(BucketCreateDTO dto) {
        BaseResult<Boolean> result = BaseResult.fail();

        try {
            CreateBucketRequest request = new CreateBucketRequest();
            request.setBucketName(dto.getName());
            request.setAcl(toACL(dto.getAclEnum()));
            client.createBucket(request);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to create bucket", e);
        }
        return result;
    }

    @Override
    public BaseResult _deleteBucket(String bucket) {
        BaseResult<Boolean> result = BaseResult.fail();

        try {
            client.deleteBucket(bucket);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to create bucket", e);
            result.setErrorMessage("删除桶失败");
        }
        return result;
    }

    @Override
    public BaseResult<List<BucketVO>> _queryBuckets() {
        BaseResult result = BaseResult.fail();
        try {
            ListBucketsRequest request = new ListBucketsRequest();
            request.setBucketType(BucketTypeEnum.OBJECT);

            List<ObsBucket> buckets = client.listBuckets(request);
            if (ListUtil.isEmpty(buckets)) {
                log.warn("buckets is empty.");
                result.value(ListUtil.empty());
                return result;
            }
            List<BucketVO> vos = buckets.stream()
                                        .map(item -> {
                                            BucketVO vo = new BucketVO();
                                            vo.setName(item.getBucketName());
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
        return client.doesObjectExist(bucket, key);
    }

    @Override
    public BaseResult<ObjectPutVO> _uploadObj(String bucket, String key, String filePath) {
        BaseResult<ObjectPutVO> result = BaseResult.fail();

        try {
            return uploadObj(bucket, key, new File(filePath));
        } catch (Exception e) {
            log.error("fail to put obj", e);
        }

        return result;
    }

    @Override
    public BaseResult<ObjectPutVO> _uploadObj(String bucket, String key, File file) {
        BaseResult<ObjectPutVO> result = BaseResult.fail();

        try {
            PutObjectRequest request = new PutObjectRequest();
            request.setBucketName(bucket);
            request.setObjectKey(key);
            request.setFile(file);
            client.putObject(request);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);
            result.value(vo);
        } catch (Exception e) {
            log.error("fail to put obj", e);
        }

        return result;
    }

    @Override
    public BaseResult<ObjectPutVO> _uploadObj(String bucket, String key, InputStream inputStream) {
        BaseResult<ObjectPutVO> result = BaseResult.fail();

        try {
            PutObjectRequest request = new PutObjectRequest();
            request.setBucketName(bucket);
            request.setObjectKey(key);
            request.setInput(inputStream);
            client.putObject(request);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);
            result.value(vo);
        } catch (Exception e) {
            log.error("fail to put obj", e);
        }

        return result;
    }


    @Override
    public BaseResult<String> _getObjSignedUrl(String bucket, String key, long expireSeconds) {
        BaseResult<String> result = BaseResult.fail();
        try {
            TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
            request.setBucketName(bucket);
            request.setObjectKey(key);
            TemporarySignatureResponse response = client.createTemporarySignature(request);
            result.value(response.getSignedUrl());
        } catch (Exception e) {
            log.error("fail to get security url", e);
        }

        return result;
    }

    @Override
    public BaseResult<String> _getObjSignedUrl(ObjectSignUrlDTO dto) {
        BaseResult<String> result = BaseResult.fail();
        try {
            TemporarySignatureRequest request = new TemporarySignatureRequest(toHttpMethodEnum(dto.getHttpMethod()), dto.getExpireSeconds());
            request.setBucketName(dto.getBucket());
            request.setObjectKey(dto.getKey());
            TemporarySignatureResponse response = client.createTemporarySignature(request);
            result.value(response.getSignedUrl());
        } catch (Exception e) {
            log.error("fail to get security url", e);
        }
        return result;
    }

    @Override
    public BaseResult<Boolean> _downloadObj(String bucket, String key, String filePath) {
        BaseResult<Boolean> result = BaseResult.fail();

        try {
            ObsObject obsObject = client.getObject(bucket, key);
            if (obsObject == null) {
                log.warn("obj is not exist");
                result.setErrorMessage("对象不存在");
                return result;
            }

            InputStream input = obsObject.getObjectContent();
            byte[] b = new byte[1024];
            FileOutputStream fos = new FileOutputStream(filePath);
            int len;
            while ((len = input.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            IOUtil.close(fos);
            IOUtil.close(input);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to download object from oss", e);
        }

        return result;
    }

    @Override
    public BaseResult<Boolean> _deleteObj(String bucket, String key) {
        BaseResult<Boolean> result = BaseResult.fail();
        try {
            client.deleteObject(bucket, key);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to delete obj", e);
            result.setErrorMessage("删除对象失败");
        }
        return result;
    }

    @Override
    public BaseResult<Boolean> _deleteObjs(String bucket, List<String> keys) {
        BaseResult<Boolean> result = BaseResult.fail();

        try {
            DeleteObjectsRequest request = new DeleteObjectsRequest();
            request.setBucketName(bucket);
            keys.forEach(key -> request.addKeyAndVersion(key));
            client.deleteObjects(request);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to delete objs", e);
        }

        return result;
    }

    @Override
    public BaseResult<List<ObjectVO>> _queryObjs(ObjectQueryDTO dto) {
        BaseResult<List<ObjectVO>> result = BaseResult.fail();
        ListObjectsRequest request = new ListObjectsRequest(dto.getBucket());
        request.setMaxKeys(dto.getMaxKeys());
        request.setPrefix(dto.getPrefix());

        try {
            ObjectListing data = client.listObjects(request);
            if (data == null) {
                log.warn("data is null");
                result.value(Collections.EMPTY_LIST);
                return result;
            }

            if (CollectionUtil.isEmpty(data.getObjects())) {
                log.info("objects count is 0");
                result.value(Collections.EMPTY_LIST);
                return result;
            }

            List<ObjectVO> vos = new ArrayList<>();
            log.info("objects count is {}", data.getObjects().size());

            for (ObsObject obsObject : data.getObjects()) {

                ObjectVO vo = new ObjectVO();
                vo.setKey(obsObject.getObjectKey());
                vos.add(vo);
            }

            result.value(vos);
        } catch (Exception e) {
            log.error("fail to query objs from oss", e);
            result.setErrorMessage("查询对象列表失败");
        }


        return result;
    }


    //------private
    private HttpMethodEnum toHttpMethodEnum(com.github.spy.sea.core.support.oss.enums.HttpMethodEnum httpMethodEnum) {
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
