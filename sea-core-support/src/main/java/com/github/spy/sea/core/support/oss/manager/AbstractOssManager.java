package com.github.spy.sea.core.support.oss.manager;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
@Slf4j
public abstract class AbstractOssManager implements OssManager {
    @Override
    public boolean init(OssConfig config) {
        return false;
    }

    @Override
    public boolean destroy() {
        return false;
    }

    @Override
    public boolean bucketExist(String bucket) {
        return false;
    }

    @Override
    public BaseResult createBucket(String bucket) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult deleteBucket(String bucket) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult queryBuckets() {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<Boolean> uploadObj(String bucket, String key, String filePath) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<Boolean> downloadObj(String bucket, String key) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<Boolean> deleteObj(String bucket, String key) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<Boolean> deleteObjs(String bucket, Iterable<String> keys) {
        return BaseResult.failMsg("不支持的操作");
    }
}
