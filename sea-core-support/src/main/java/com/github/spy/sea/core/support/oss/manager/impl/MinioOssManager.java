package com.github.spy.sea.core.support.oss.manager.impl;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.enums.OssTypeEnum;
import com.github.spy.sea.core.support.oss.manager.AbstractOssManager;
import com.github.spy.sea.core.support.oss.vo.BucketVO;
import com.github.spy.sea.core.support.oss.vo.ObjectPutVO;
import com.github.spy.sea.core.util.ListUtil;
import com.google.common.base.Preconditions;
import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void init(OssConfig config) {
        log.info("init minio oss, config={}", config);
        client = MinioClient.builder()
                            .endpoint(config.getEndpoint())
                            .credentials(config.getAccessKey(), config.getSecretKey())
                            .build();
    }

    @Override
    public void destroy() {
        log.info("destroy minio oss");
        client = null;
    }

    @Override
    public String getType() {
        return OssTypeEnum.MINIO.getCode();
    }

    @Override
    public boolean checkBucketExist(String bucket) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");

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
    public BaseResult createBucket(String bucket) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");

        BaseResult result = BaseResult.fail();
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
    public BaseResult deleteBucket(String bucket) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");

        BaseResult result = BaseResult.fail();
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
    public BaseResult<List<BucketVO>> queryBuckets() {
        BaseResult<List<BucketVO>> result = BaseResult.fail();

        try {
            List<Bucket> buckets = client.listBuckets();
            if (ListUtil.isEmpty(buckets)) {
                result.value(ListUtil.empty());
                return result;
            }

            List<BucketVO> vos = buckets.stream()
                                        .map(item -> {
                                            BucketVO vo = new BucketVO();
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
    public boolean checkObjExist(String bucket, String key) {

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
    public BaseResult<ObjectPutVO> uploadObj(String bucket, String key, String filePath) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");
        Preconditions.checkNotNull(key, "key不能为空");
        Preconditions.checkNotNull(filePath, "file不能为空");

        BaseResult<ObjectPutVO> result = BaseResult.fail();
        try {
            UploadObjectArgs args = UploadObjectArgs.builder()
                                                    .bucket(bucket)
                                                    .object(key)
                                                    .filename(filePath)
                                                    .build();
            client.uploadObject(args);

            ObjectPutVO vo = new ObjectPutVO();
            vo.setKey(key);

            result.value(vo);
        } catch (Exception e) {
            log.error("fail to upload obj", e);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult<Boolean> downloadObj(String bucket, String key, String filePath) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");
        Preconditions.checkNotNull(key, "key不能为空");

        BaseResult<Boolean> result = BaseResult.fail();
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                                              .bucket(bucket)
                                              .object(key)
                                              .build();
            GetObjectResponse response = client.getObject(args);

            result.value(true);
        } catch (Exception e) {
            log.error("fail to download obj", e);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult<Boolean> deleteObj(String bucket, String key) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");
        Preconditions.checkNotNull(key, "key不能为空");

        BaseResult<Boolean> result = BaseResult.fail();
        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                                                    .bucket(bucket)
                                                    .object(key)
                                                    .build();
            client.removeObject(args);
            result.setData(true);
        } catch (Exception e) {
            log.error("fail to delete obj", e);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult<Boolean> deleteObjs(String bucket, List<String> keys) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");
        Preconditions.checkNotNull(keys, "keys不能为空");

        BaseResult<Boolean> result = BaseResult.fail();

        try {
            List<DeleteObject> list = new ArrayList<>();
            keys.forEach(key -> list.add(new DeleteObject(key)));

            if (list.isEmpty()) {
                result.setErrorMessage("keys is empty.");
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
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }
}
