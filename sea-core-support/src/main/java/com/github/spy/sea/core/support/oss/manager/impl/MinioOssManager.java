package com.github.spy.sea.core.support.oss.manager.impl;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.manager.AbstractOssManager;
import com.google.common.base.Preconditions;
import io.minio.*;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
    public boolean init(OssConfig config) {

        client = MinioClient.builder()
                            .endpoint(config.getEndpoint())
                            .credentials(config.getAccessKey(), config.getSecretKey())
                            .build();
        return true;
    }

    @Override
    public boolean destroy() {
        client = null;
        return true;
    }

    @Override
    public boolean bucketExist(String bucket) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");
        return super.bucketExist(bucket);
    }

    @Override
    public BaseResult createBucket(String bucket) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");
        return super.createBucket(bucket);
    }

    @Override
    public BaseResult deleteBucket(String bucket) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");
        return super.deleteBucket(bucket);
    }

    @Override
    public BaseResult queryBuckets() {
        return super.queryBuckets();
    }

    @Override
    public BaseResult<Boolean> uploadObj(String bucket, String key, String filePath) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");
        Preconditions.checkNotNull(key, "key不能为空");
        Preconditions.checkNotNull(filePath, "file不能为空");

        BaseResult<Boolean> result = BaseResult.fail();
        try {
            UploadObjectArgs args = UploadObjectArgs.builder()
                                                    .bucket(bucket)
                                                    .object(key)
                                                    .filename(filePath)
                                                    .build();
            client.uploadObject(args);

        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
            return result;
        }
        result.setData(null);
        return result;
    }

    @Override
    public BaseResult<Boolean> downloadObj(String bucket, String key) {
        Preconditions.checkNotNull(bucket, "bucket不能为空");
        Preconditions.checkNotNull(key, "key不能为空");

        BaseResult<Boolean> result = BaseResult.fail();
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                                              .bucket(bucket)
                                              .object(key)
                                              .build();
            client.getObject(args);

        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
            return result;
        }
        result.setData(null);
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
        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
            return result;
        }
        result.setData(true);
        return result;
    }

    @Override
    public BaseResult<Boolean> deleteObjs(String bucket, Iterable<String> keys) {
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
        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
            return result;
        }
        result.setData(true);
        return result;
    }
}
