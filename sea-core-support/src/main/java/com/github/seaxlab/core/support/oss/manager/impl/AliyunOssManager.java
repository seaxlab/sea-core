package com.github.seaxlab.core.support.oss.manager.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.*;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.support.oss.dto.*;
import com.github.seaxlab.core.support.oss.enums.AclEnum;
import com.github.seaxlab.core.support.oss.enums.HttpMethodEnum;
import com.github.seaxlab.core.support.oss.enums.OssTypeEnum;
import com.github.seaxlab.core.support.oss.manager.AbstractOssManager;
import com.github.seaxlab.core.support.oss.vo.BucketVO;
import com.github.seaxlab.core.support.oss.vo.ObjectPutVO;
import com.github.seaxlab.core.support.oss.vo.ObjectVO;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.ListUtil;
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
    public void _init(OssConfig config) {
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(config.getAccessKey(), config.getSecretKey());
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

    public Result _createBucket(String bucket) {
        Result result = Result.fail();
        try {
            client.createBucket(bucket);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to create bucket", e);
        }

        return result;
    }

    @Override
    public Result _createBucket(BucketCreateDTO dto) {
        Result result = Result.fail();
        try {
            CreateBucketRequest request = new CreateBucketRequest(dto.getName());
            request.setCannedACL(toACL(dto.getAclEnum()));
            client.createBucket(request);
            result.value(true);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to create bucket", e);
        }

        return result;
    }

    public Result _deleteBucket(String bucket) {
        Result result = Result.fail();
        try {
            client.deleteBucket(bucket);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to delete bucket", e);
        }

        return result;
    }

    public Result<List<BucketVO>> _queryBuckets() {
        Result result = Result.fail();
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

    public Result<ObjectPutVO> _uploadObj(String bucket, String key, String filePath) {
        Result<ObjectPutVO> result = Result.fail();

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


    public Result<ObjectPutVO> _uploadObj(String bucket, String key, File file) {
        Result<ObjectPutVO> result = Result.fail();

        try {
            PutObjectRequest request = new PutObjectRequest(bucket, key, file);
            client.putObject(request);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);
            result.value(vo);
        } catch (Exception e) {
            log.error("fail to put obj", e);
            result.setMsg("上传文件失败");
        }

        return result;
    }

    public Result<ObjectPutVO> _uploadObj(String bucket, String key, InputStream inputStream) {
        Result<ObjectPutVO> result = Result.fail();

        try {
            PutObjectRequest request = new PutObjectRequest(bucket, key, inputStream);
            client.putObject(request);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);
            result.value(vo);
        } catch (Exception e) {
            log.error("fail to put obj", e);
            result.setMsg("上传文件失败");
        }

        return result;
    }

    @Override
    public Result<ObjectPutVO> _uploadObj(ObjectUploadDTO dto) {
        Result<ObjectPutVO> result = Result.fail();

        try {
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

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(dto.getKey());
            result.value(vo);
        } catch (Exception e) {
            log.error("fail to put obj", e);
            result.setMsg("上传文件失败");
        }

        return result;
    }

    public Result<String> _getObjSignedUrl(String bucket, String key, long expireSeconds) {
        Result<String> result = Result.fail();
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

    public Result<String> _getObjSignedUrl(ObjectSignUrlDTO dto) {
        Result<String> result = Result.fail();
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

    public Result<Boolean> _downloadObj(String bucket, String key, String newFilePath) {
        Result<Boolean> result = Result.fail();

        try {
            OSSObject object = client.getObject(bucket, key);
            if (object == null) {
                result.setMsg("对象不存在");
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
            result.setMsg("下载文件失败");
        }

        return result;
    }

    public Result<Boolean> _deleteObj(String bucket, String key) {
        Result<Boolean> result = Result.fail();
        try {
            client.deleteObject(bucket, key);
            result.value(true);
        } catch (Exception e) {
            log.error("fail to delete obj", e);
            result.setMsg("删除对象失败");
        }
        return result;
    }

    public Result<Boolean> _deleteObjs(String bucket, List<String> keys) {
        Precondition.checkNotNull(bucket);

        Result<Boolean> result = Result.fail();

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

    public Result<List<ObjectVO>> _queryObjs(ObjectQueryDTO dto) {
        Precondition.checkNotNull(dto.getBucket());

        Result<List<ObjectVO>> result = Result.fail();
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
            result.setMsg("查询对象列表失败");
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
