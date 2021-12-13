package com.github.spy.sea.core.support.oss.manager;

import com.github.spy.sea.core.exception.Precondition;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.ObjectQueryDTO;
import com.github.spy.sea.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.enums.OssTypeEnum;
import com.github.spy.sea.core.support.oss.vo.BucketVO;
import com.github.spy.sea.core.support.oss.vo.ObjectPutVO;
import com.github.spy.sea.core.support.oss.vo.ObjectVO;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

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
    public void init(OssConfig config) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String getType() {
        return OssTypeEnum.UNKNOWN.getCode();
    }

    @Override
    public boolean checkBucketExist(String bucket) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        return _checkBucketExist(bucket);
    }

    @Override
    public BaseResult createBucket(String bucket) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        return _createBucket(bucket);
    }

    @Override
    public BaseResult deleteBucket(String bucket) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        return _deleteBucket(bucket);
    }

    @Override
    public BaseResult<List<BucketVO>> queryBuckets() {
        return _queryBuckets();
    }

    @Override
    public boolean checkObjExist(String bucket, String key) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(key, "key cannot be empty");

        return _checkObjExist(bucket, key);
    }

    @Override
    public BaseResult<ObjectPutVO> uploadObj(String bucket, String key, String filePath) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(key, "key cannot be empty");
        Precondition.checkNotEmpty(filePath, "filePath cannot be empty");

        return _uploadObj(bucket, key, filePath);
    }

    @Override
    public BaseResult<ObjectPutVO> uploadObj(String bucket, String key, File file) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(key, "key cannot be empty");
        Precondition.checkNotNull(file, "file cannot be empty");

        return _uploadObj(bucket, key, file);
    }

    @Override
    public BaseResult<String> getObjSignedUrl(String bucket, String key, long expireSeconds) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(key, "key cannot be empty");

        expireSeconds = expireSeconds < 0 ? 600 /*second*/ : expireSeconds;

        return _getObjSignedUrl(bucket, key, expireSeconds);
    }

    @Override
    public BaseResult<String> getObjSignedUrl(ObjectSignUrlDTO dto) {
        Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
        Precondition.checkNotEmpty(dto.getKey(), "key cannot be empty");

        if (dto.getExpireSeconds() < 0) {
            dto.setExpireSeconds(600);
        }

        return _getObjSignedUrl(dto);
    }

    @Override
    public BaseResult<Boolean> downloadObj(String bucket, String key, String filePath) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(key, "key cannot be empty");
        log.info("download obj bucket={},key={},filePath={}", bucket, key, filePath);

        return _downloadObj(bucket, key, filePath);
    }

    @Override
    public BaseResult<Boolean> deleteObj(String bucket, String key) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(key, "key cannot be empty");
        log.info("delete obj bucket={},key={}", bucket, key);

        return _deleteObj(bucket, key);
    }

    @Override
    public BaseResult<Boolean> deleteObjs(String bucket, List<String> keys) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(keys, "keys cannot be empty");
        log.info("delete obj bucket={},keys={}", bucket, keys);

        return _deleteObjs(bucket, keys);
    }

    @Override
    public BaseResult<List<ObjectVO>> queryObjs(ObjectQueryDTO dto) {
        Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");

        return _queryObjs(dto);
    }


    // ------------- simple overwrite
    public boolean _checkBucketExist(String bucket) {
        return false;
    }

    public BaseResult _createBucket(String bucket) {
        return BaseResult.failMsg("不支持的操作");
    }

    public BaseResult _deleteBucket(String bucket) {
        return BaseResult.failMsg("不支持的操作");
    }

    public BaseResult<List<BucketVO>> _queryBuckets() {
        return BaseResult.failMsg("不支持的操作");
    }

    public boolean _checkObjExist(String bucket, String key) {
        return false;
    }

    public BaseResult<ObjectPutVO> _uploadObj(String bucket, String key, String filePath) {
        return BaseResult.failMsg("不支持的操作");
    }

    public BaseResult<ObjectPutVO> _uploadObj(String bucket, String key, File file) {
        return BaseResult.failMsg("不支持的操作");
    }

    public BaseResult<String> _getObjSignedUrl(String bucket, String key, long expireSeconds) {
        return BaseResult.failMsg("不支持的操作");
    }

    public BaseResult<String> _getObjSignedUrl(ObjectSignUrlDTO dto) {
        return BaseResult.failMsg("不支持的操作");
    }

    public BaseResult<Boolean> _downloadObj(String bucket, String key, String filePath) {
        return BaseResult.failMsg("不支持的操作");
    }

    public BaseResult<Boolean> _deleteObj(String bucket, String key) {
        return BaseResult.failMsg("不支持的操作");
    }

    public BaseResult<Boolean> _deleteObjs(String bucket, List<String> keys) {
        return BaseResult.failMsg("不支持的操作");
    }

    public BaseResult<List<ObjectVO>> _queryObjs(ObjectQueryDTO dto) {
        return BaseResult.failMsg("不支持的操作");
    }

}
