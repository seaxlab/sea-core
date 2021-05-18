package com.github.spy.sea.core.support.oss.manager.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectResult;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.manager.AbstractOssManager;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
@Slf4j
public class AliyunOssManager extends AbstractOssManager {

    private OSSClient client;

    @Override
    public boolean init(OssConfig config) {

        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(config.getAccessKey(), config.getSecretKey());
        client = new OSSClient(config.getEndpoint(), credentialsProvider, null);
        return true;
    }

    @Override
    public boolean destroy() {
        client = null;
        return true;
    }

    @Override
    public boolean bucketExist(String bucket) {
        return super.bucketExist(bucket);
    }

    @Override
    public BaseResult createBucket(String bucket) {
        return super.createBucket(bucket);
    }

    @Override
    public BaseResult deleteBucket(String bucket) {
        return super.deleteBucket(bucket);
    }

    @Override
    public BaseResult queryBuckets() {
        return super.queryBuckets();
    }

    @Override
    public BaseResult<Boolean> uploadObj(String bucket, String key, String filePath) {
        BaseResult<Boolean> result = BaseResult.fail();

        try {
            InputStream inputStream = new FileInputStream(filePath);
            PutObjectResult ret = client.putObject(bucket, key, inputStream);
        } catch (Exception e) {
            return result;
        }

        result.value(true);
        return result;
    }

    @Override
    public BaseResult<Boolean> downloadObj(String bucket, String key) {
        return super.downloadObj(bucket, key);
    }

    @Override
    public BaseResult<Boolean> deleteObj(String bucket, String key) {
        return super.deleteObj(bucket, key);
    }

    @Override
    public BaseResult<Boolean> deleteObjs(String bucket, Iterable<String> keys) {
        return super.deleteObjs(bucket, keys);
    }
}
