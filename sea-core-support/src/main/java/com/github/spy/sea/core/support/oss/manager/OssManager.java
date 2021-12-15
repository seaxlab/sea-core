package com.github.spy.sea.core.support.oss.manager;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.ObjectQueryDTO;
import com.github.spy.sea.core.support.oss.dto.ObjectSignUrlDTO;
import com.github.spy.sea.core.support.oss.dto.ObjectUrlDTO;
import com.github.spy.sea.core.support.oss.dto.OssConfig;
import com.github.spy.sea.core.support.oss.vo.BucketVO;
import com.github.spy.sea.core.support.oss.vo.ObjectPutVO;
import com.github.spy.sea.core.support.oss.vo.ObjectVO;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/16
 * @since 1.0
 */
public interface OssManager {

    /**
     * init
     *
     * @param config
     * @return
     */
    void init(OssConfig config);

    /**
     * destroy
     *
     * @return
     */
    void destroy();

    /**
     * return current oss type
     *
     * @return
     */
    String getType();

    /**
     * 校验bucket是否存在
     *
     * @param bucket
     * @return
     */
    boolean checkBucketExist(String bucket);

    /**
     * 创建bucket
     *
     * @param bucket
     * @return
     */
    BaseResult createBucket(String bucket);

    /**
     * 删除bucket
     *
     * @param bucket
     * @return
     */
    BaseResult deleteBucket(String bucket);

    /**
     * 查询所有bucket
     *
     * @return
     */
    BaseResult<List<BucketVO>> queryBuckets();

    /**
     * check obj exist
     *
     * @param bucket
     * @param key
     * @return
     */
    boolean checkObjExist(String bucket, String key);

    /**
     * upload obj by file path
     *
     * @param bucket
     * @param key
     * @param filePath
     * @return
     */
    BaseResult<ObjectPutVO> uploadObj(String bucket, String key, String filePath);

    /**
     * upload obj by file
     *
     * @param bucket
     * @param key
     * @param file
     * @return
     */
    BaseResult<ObjectPutVO> uploadObj(String bucket, String key, File file);

    /**
     * upload obj by stream
     *
     * @param bucket
     * @param key
     * @param inputStream
     * @return
     */
    BaseResult<ObjectPutVO> uploadObj(String bucket, String key, InputStream inputStream);

    /**
     * get object url.
     *
     * @param dto
     * @return
     */
    BaseResult<String> getObjUrl(ObjectUrlDTO dto);

    /**
     * get obj signed url
     *
     * @param bucket
     * @param key
     * @param expireSeconds
     * @return
     */
    BaseResult<String> getObjSignedUrl(String bucket, String key, long expireSeconds);

    /**
     * get obj signed url
     *
     * @param dto
     * @return
     */
    BaseResult<String> getObjSignedUrl(ObjectSignUrlDTO dto);

    /**
     * 下载文件
     *
     * @param bucket
     * @param key
     * @param filePath 文件路径
     * @return
     */
    BaseResult<Boolean> downloadObj(String bucket, String key, String filePath);

    /**
     * 删除对象
     *
     * @param bucket
     * @param key
     * @return
     */
    BaseResult<Boolean> deleteObj(String bucket, String key);

    /**
     * 批量删除对象
     *
     * @param bucket
     * @param keys
     * @return
     */
    BaseResult<Boolean> deleteObjs(String bucket, List<String> keys);

    /**
     * query objs
     *
     * @param dto
     * @return
     */
    BaseResult<List<ObjectVO>> queryObjs(ObjectQueryDTO dto);
}
