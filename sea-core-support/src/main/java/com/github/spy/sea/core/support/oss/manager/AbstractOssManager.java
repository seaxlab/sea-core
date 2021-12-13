package com.github.spy.sea.core.support.oss.manager;

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
    public BaseResult<List<BucketVO>> queryBuckets() {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public boolean checkObjExist(String bucket, String key) {
        return false;
    }

    @Override
    public BaseResult<ObjectPutVO> uploadObj(String bucket, String key, String filePath) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<ObjectPutVO> uploadObj(String bucket, String key, File file) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<String> getObjSignedUrl(String bucket, String key, long expireSeconds) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<String> getObjSignedUrl(ObjectSignUrlDTO dto) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<Boolean> downloadObj(String bucket, String key, String filePath) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<Boolean> deleteObj(String bucket, String key) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<Boolean> deleteObjs(String bucket, List<String> keys) {
        return BaseResult.failMsg("不支持的操作");
    }

    @Override
    public BaseResult<List<ObjectVO>> queryObjs(ObjectQueryDTO dto) {
        return BaseResult.failMsg("不支持的操作");
    }
}
