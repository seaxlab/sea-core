package com.github.spy.sea.core.support.oss.manager;

import com.github.spy.sea.core.exception.Precondition;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.ObjectQueryDTO;
import com.github.spy.sea.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.spy.sea.core.support.oss.dto.ObjectUrlDTO;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.enums.OssTypeEnum;
import com.github.spy.sea.core.support.oss.vo.BucketVO;
import com.github.spy.sea.core.support.oss.vo.ObjectPutVO;
import com.github.spy.sea.core.support.oss.vo.ObjectVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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

    protected OssConfig ossConfig;

    @Override
    public void init(OssConfig config) {
        Precondition.checkNotNull(config);
        Precondition.checkNotEmpty(config.getEndpoint(), "Endpoint cannot be empty");
        Precondition.checkNotEmpty(config.getAccessKey(), "AccessKey cannot be empty");
        Precondition.checkNotEmpty(config.getSecretKey(), "SecretKey cannot be empty");
        log.info("init {} oss manager, config={}", getType(), config);

        this.ossConfig = config;
        _init(config);
        log.info("init {} oss manager successfully", getType());
    }

    @Override
    public void destroy() {
        log.info("destroy {} oss manager", getType());
        _destroy();
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
        log.info("create bucket={}", bucket);

        BaseResult result = _createBucket(bucket);

        if (result.isOk()) {
            log.info("create bucket successfully");
        }

        return result;
    }

    @Override
    public BaseResult deleteBucket(String bucket) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        log.info("try to delete bucket={}", bucket);

        BaseResult result = _deleteBucket(bucket);
        if (result.isOk()) {
            log.info("delete bucket successfully");
        }

        return result;
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
        log.info("upload obj bucket={},key={},filePath={}", bucket, key, filePath);

        BaseResult result = _uploadObj(bucket, key, filePath);
        if (result.isOk()) {
            log.info("upload obj successfully");
        }
        return result;
    }

    @Override
    public BaseResult<ObjectPutVO> uploadObj(String bucket, String key, File file) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(key, "key cannot be empty");
        Precondition.checkNotNull(file, "file cannot be empty");
        log.info("upload obj bucket={},key={} by file", bucket, key);

        BaseResult result = _uploadObj(bucket, key, file);
        if (result.isOk()) {
            log.info("upload obj successfully");
        }
        return result;
    }

    @Override
    public BaseResult<String> getObjUrl(ObjectUrlDTO dto) {
        Precondition.checkNotNull(dto);
        Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
        Precondition.checkNotEmpty(dto.getKey(), "key cannot be empty");

        return _getObjUrl(dto);
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

        BaseResult result = _downloadObj(bucket, key, filePath);
        if (result.isOk()) {
            log.info("download obj successfully");
        }
        return result;
    }

    @Override
    public BaseResult<Boolean> deleteObj(String bucket, String key) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(key, "key cannot be empty");
        log.info("delete obj bucket={},key={}", bucket, key);

        BaseResult result = _deleteObj(bucket, key);
        if (result.isOk()) {
            log.info("delete obj successfully");
        }
        return result;
    }

    @Override
    public BaseResult<Boolean> deleteObjs(String bucket, List<String> keys) {
        Precondition.checkNotEmpty(bucket, "bucket cannot be empty");
        Precondition.checkNotEmpty(keys, "keys cannot be empty");
        log.info("delete objs bucket={},keys={}", bucket, keys);

        BaseResult result = _deleteObjs(bucket, keys);
        if (result.isOk()) {
            log.info("delete objs successfully");
        }
        return result;
    }

    @Override
    public BaseResult<List<ObjectVO>> queryObjs(ObjectQueryDTO dto) {
        Precondition.checkNotEmpty(dto.getBucket(), "bucket cannot be empty");
        if (dto.getMaxKeys() <= 0 || dto.getMaxKeys() >= 1000) {
            log.warn("max keys[{}] is invalid", dto.getMaxKeys());
            dto.setMaxKeys(100);
        }

        return _queryObjs(dto);
    }


    // ------------- simple overwrite
    public void _init(OssConfig config) {
    }

    public void _destroy() {
    }

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

    public BaseResult<String> _getObjUrl(ObjectUrlDTO dto) {
        BaseResult<String> result = BaseResult.fail();
        String url = "";
        if (dto.isCustomDomainFlag()) {
            url = ossConfig.getEndpoint() + "/" + dto.getKey();
        } else {
            // default url format: http://bucket.xx.com/key
            String bucketUrl = StringUtils.replace(ossConfig.getEndpoint(), "://", "://" + dto.getBucket() + ".");
            url = bucketUrl + "/" + dto.getKey();
        }

        result.value(url);
        return result;
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
