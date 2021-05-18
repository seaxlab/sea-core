package com.github.spy.sea.core.support.oss.manager;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.oss.dto.OssConfig;

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
    boolean init(OssConfig config);

    /**
     * destroy
     *
     * @return
     */
    boolean destroy();

    /**
     * 校验bucket是否存在
     *
     * @param bucket
     * @return
     */
    boolean bucketExist(String bucket);

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
    BaseResult queryBuckets();

    /**
     * 上传文件
     *
     * @param bucket
     * @param key
     * @param filePath
     * @return
     */
    BaseResult<Boolean> uploadObj(String bucket, String key, String filePath);

    /**
     * 下载文件
     *
     * @param bucket
     * @param key
     * @return
     */
    BaseResult<Boolean> downloadObj(String bucket, String key);

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
    BaseResult<Boolean> deleteObjs(String bucket, Iterable<String> keys);
}
