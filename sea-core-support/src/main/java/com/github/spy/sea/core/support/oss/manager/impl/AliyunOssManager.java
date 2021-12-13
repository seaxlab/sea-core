package com.github.spy.sea.core.support.oss.manager.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.*;
import com.github.spy.sea.core.exception.Precondition;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.ObjectQueryDTO;
import com.github.spy.sea.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.enums.HttpMethodEnum;
import com.github.spy.sea.core.support.oss.enums.OssTypeEnum;
import com.github.spy.sea.core.support.oss.manager.AbstractOssManager;
import com.github.spy.sea.core.support.oss.vo.BucketVO;
import com.github.spy.sea.core.support.oss.vo.ObjectPutVO;
import com.github.spy.sea.core.support.oss.vo.ObjectVO;
import com.github.spy.sea.core.util.CollectionUtil;
import com.github.spy.sea.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public void init(OssConfig config) {
        log.info("init aliyun oss manager");
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(config.getAccessKey(), config.getSecretKey());
        client = new OSSClient(config.getEndpoint(), credentialsProvider, null);
    }

    @Override
    public void destroy() {
        log.info("destroy aliyun oss manager");
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

    public BaseResult _createBucket(String bucket) {
        BaseResult result = BaseResult.fail();
        try {
            client.createBucket(bucket);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to create bucket", e);
        }

        return result;
    }

    public BaseResult _deleteBucket(String bucket) {

        BaseResult result = BaseResult.fail();
        try {
            client.deleteBucket(bucket);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to delete bucket", e);
        }

        return result;
    }

    public BaseResult<List<BucketVO>> _queryBuckets() {
        BaseResult result = BaseResult.fail();
        try {
            List<Bucket> buckets = client.listBuckets();
            if (ListUtil.isEmpty(buckets)) {
                result.value(ListUtil.empty());
                return result;
            }
            List<BucketVO> vos = buckets.stream()
                                        .map(item -> {
                                            BucketVO vo = new BucketVO();
                                            vo.setName(item.getName());
                                            return vo;
                                        }).collect(Collectors.toList());
            result.value(vos);
        } catch (Exception e) {
            log.error("fail to query buckets", e);
        }

        return result;
    }

    public boolean _checkObjExist(String bucket, String key) {
        return client.doesObjectExist(bucket, key);
    }

    public BaseResult<ObjectPutVO> _uploadObj(String bucket, String key, String filePath) {
        BaseResult<ObjectPutVO> result = BaseResult.fail();

        try {
            InputStream inputStream = new FileInputStream(filePath);
            PutObjectResult ret = client.putObject(bucket, key, inputStream);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);
            result.value(vo);
        } catch (Exception e) {
            log.error("fail to upload obj", e);
        }
        return result;
    }


    public BaseResult<ObjectPutVO> _uploadObj(String bucket, String key, File file) {
        Precondition.checkNotNull(bucket);
        Precondition.checkNotNull(key);

        BaseResult<ObjectPutVO> result = BaseResult.fail();

        try {
            PutObjectRequest request = new PutObjectRequest(bucket, key, file);
            client.putObject(request);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);
            result.value(vo);
        } catch (Exception e) {
            log.error("fail to put obj", e);
            result.setErrorMessage("上传文件失败");
        }

        return result;
    }

    public BaseResult<String> _getObjSignedUrl(String bucket, String key, long expireSeconds) {
        BaseResult<String> result = BaseResult.fail();
        try {
            Date now = new Date();
            Date expireDate = DateUtils.addSeconds(now, (int) expireSeconds);
            URL url = client.generatePresignedUrl(bucket, key, expireDate, HttpMethod.GET);
            result.value(url.toString());
        } catch (Exception e) {
            log.error("fail to get security url", e);
        }
        return result;
    }

    public BaseResult<String> _getObjSignedUrl(ObjectSignUrlDTO dto) {
        BaseResult<String> result = BaseResult.fail();
        try {
            Date now = new Date();
            Date expireDate = DateUtils.addSeconds(now, (int) dto.getExpireSeconds());
            URL url = client.generatePresignedUrl(dto.getBucket(), dto.getKey(), expireDate, toHttpMethodEnum(dto.getHttpMethod()));
            result.value(url.toString());
        } catch (Exception e) {
            log.error("fail to get security url", e);
        }

        return result;
    }

    public BaseResult<Boolean> _downloadObj(String bucket, String key, String newFilePath) {
        BaseResult<Boolean> result = BaseResult.fail();

        try {
            OSSObject object = client.getObject(bucket, key);
            if (object == null) {
                result.setErrorMessage("对象不存在");
                return result;
            }

            InputStream input = object.getObjectContent();
            byte[] b = new byte[1024];
            FileOutputStream fos = new FileOutputStream(newFilePath);
            int len;
            while ((len = input.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            fos.close();
            input.close();
            result.value(true);
        } catch (Exception e) {
            log.error("fail to download object from oss", e);
            result.setErrorMessage("下载文件失败");
        }

        return result;
    }

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

    public BaseResult<Boolean> _deleteObjs(String bucket, List<String> keys) {
        Precondition.checkNotNull(bucket);

        BaseResult<Boolean> result = BaseResult.fail();

        try {
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucket);
            request.setKeys(keys);
            client.deleteObjects(request);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to delete objs", e);
        }

        return result;
    }

    public BaseResult<List<ObjectVO>> _queryObjs(ObjectQueryDTO dto) {
        Precondition.checkNotNull(dto.getBucket());

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

            if (CollectionUtil.isEmpty(data.getObjectSummaries())) {
                log.info("objects count is 0");
                result.value(Collections.EMPTY_LIST);
                return result;
            }

            List<ObjectVO> vos = new ArrayList<>();
            log.info("objects count is {}", data.getObjectSummaries().size());

            for (OSSObjectSummary objectSummary : data.getObjectSummaries()) {

                ObjectVO vo = new ObjectVO();
                vo.setKey(objectSummary.getKey());
                vos.add(vo);
            }

            result.value(vos);
        } catch (Exception e) {
            log.error("fail to query objs from oss", e);
            result.setErrorMessage("查询对象列表失败");
        }


        return result;
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
}
