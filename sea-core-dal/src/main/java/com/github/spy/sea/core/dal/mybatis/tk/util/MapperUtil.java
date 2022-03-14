package com.github.spy.sea.core.dal.mybatis.tk.util;

import com.github.pagehelper.PageHelper;
import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.exception.Precondition;
import com.github.spy.sea.core.model.PageInfo;
import com.github.spy.sea.core.model.common.ModelConst;
import com.github.spy.sea.core.model.util.PageUtil;
import com.github.spy.sea.core.util.ListUtil;
import com.github.spy.sea.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/6/3
 * @since 1.0
 */
@Slf4j
public final class MapperUtil {

    private static final int DEFAULT_DELETE_PAGE_SIZE = 500;

    /**
     * check code.
     *
     * @param mapper
     * @param code
     * @param <T>
     */
    @Deprecated // mapper已经被MapperProxy代理过，层次嵌套太深不建议使用
    public static <T> void checkCode(Mapper<T> mapper, String code) {

        Object obj;
        if (mapper instanceof Proxy) {
            obj = ((Class) mapper.getClass().getGenericInterfaces()[0]).getClass().getGenericInterfaces()[0];
        } else {
            obj = mapper;
        }

        Class<?> clazz = ReflectUtil.getSingleGenericClass(obj);

        checkCode(clazz, mapper, code);
    }

    /**
     * check code
     *
     * @param clazz
     * @param mapper
     * @param code
     */
    public static void checkCode(Class<?> clazz, Mapper mapper, String code) {
        Example example = new Example(clazz);
        ExampleUtil.setValue(example, "code", code);
        ExampleUtil.setIsDeletedFlag(example);

        int count = mapper.selectCountByExample(example);
        if (count > 0) {
            ExceptionHandler.publishMsg("已存在相同的编码");
        }
    }

    /**
     * base query by id.
     *
     * @param mapper
     * @param id
     * @param <T>
     * @return
     */
    @Deprecated // mapper已经被MapperProxy代理过，层次嵌套太深不建议使用
    public static <T> T baseQueryById(Mapper<T> mapper, Long id) {
        Class<?> clazz = ReflectUtil.getSingleGenericClass(mapper);
        return baseQueryById(clazz, mapper, id);
    }

    /**
     * basic query by id
     * <p>
     * must have id,is_deleted field
     * </p>
     *
     * @param clazz
     * @param mapper
     * @param id
     * @param <T>
     * @return
     */
    public static <T> T baseQueryById(Class<?> clazz, Mapper<T> mapper, Long id) {
        Example example = new Example(clazz);
        ExampleUtil.setValue(example, "id", id);
        ExampleUtil.setIsDeletedFlag(example);

        T record = mapper.selectOneByExample(example);
        if (record == null) {
            ExceptionHandler.publishMsg("记录不存在");
        }
        return record;
    }

    /**
     * base query by code.
     *
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    @Deprecated // mapper已经被MapperProxy代理过，层次嵌套太深不建议使用
    public static <T> T baseQueryByCode(Mapper<T> mapper, String code) {

        if (mapper instanceof MapperProxy) {
            MapperProxy proxy = (MapperProxy) mapper;
        }
        Class<?> clazz = ReflectUtil.getSingleGenericClass(mapper);

        return baseQueryByCode(clazz, mapper, code);
    }

    /**
     * basic query by code
     * <p>
     * must have id,is_deleted field
     * </p>
     *
     * @param clazz
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    public static <T> T baseQueryByCode(Class<?> clazz, Mapper<T> mapper, String code) {
        return baseQuery(clazz, mapper, "code", code);
    }

    /**
     * base query for common.
     *
     * @param clazz
     * @param mapper
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T baseQuery(Class<?> clazz, Mapper<T> mapper, String key, String value) {
        Example example = new Example(clazz);
        ExampleUtil.setValue(example, key, value);
        ExampleUtil.setIsDeletedFlag(example);

        T record = mapper.selectOneByExample(example);
        if (record == null) {
            ExceptionHandler.publishMsg("记录不存在");
        }
        return record;
    }


    /**
     * basic update selective field by code.
     *
     * @param record
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    public static <T> int baseUpdateSelectiveByCode(T record, Mapper<T> mapper, String code) {
        return baseUpdateSelective(record, mapper, "code", code);
    }

    /**
     * base update selective for common.
     *
     * @param record
     * @param mapper
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> int baseUpdateSelective(T record, Mapper<T> mapper, String key, String value) {
        Example example = new Example(record.getClass());
        ExampleUtil.setValue(example, key, value);
        ExampleUtil.setIsDeletedFlag(example);

        return mapper.updateByExampleSelective(record, example);
    }

    /**
     * basic update all field by code.
     *
     * @param record
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    public static <T> int baseUpdateByCode(T record, Mapper<T> mapper, String code) {
        return baseUpdate(record, mapper, "code", code);
    }

    /**
     * basic update all field for common.
     *
     * @param record
     * @param mapper
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> int baseUpdate(T record, Mapper<T> mapper, String key, String value) {
        Example example = new Example(record.getClass());
        ExampleUtil.setValue(example, key, value);
        ExampleUtil.setIsDeletedFlag(example);

        return mapper.updateByExample(record, example);
    }

    /**
     * update single record by version (not include isDeletedFlag)
     *
     * @param mapper
     * @param updateRecord
     * @param id
     * @param version
     * @param <T>
     * @return
     */
    public static <T> boolean updateByVersion(Mapper<T> mapper, T updateRecord, Long id, Integer version) {
        return updateByVersion(mapper, updateRecord, id, version, false);
    }

    /**
     * update single record by version
     *
     * @param mapper
     * @param updateRecord
     * @param id
     * @param version
     * @param <T>
     * @return
     */
    public static <T> boolean updateByVersion(Mapper<T> mapper, T updateRecord, Long id, Integer version, boolean isDeletedFlag) {
        Precondition.checkNotNull(mapper);
        Precondition.checkNotNull(updateRecord);
        Precondition.checkNotNull(id);
        Precondition.checkNotNull(version);

        Example example = new Example(updateRecord.getClass());
        Example.Criteria criteria = example.createCriteria();
        ExampleUtil.set(criteria, ModelConst.ID, id);
        ExampleUtil.set(criteria, ModelConst.VERSION, version);
        if (isDeletedFlag) {
            ExampleUtil.setIsDeletedFlag(example);
        }

        int rowCount = mapper.updateByExampleSelective(updateRecord, example);
        boolean sucFlag = rowCount > 0;
        if (sucFlag) {
            log.info("update {}[{},{}] by version, row count={}",
                    updateRecord.getClass().getSimpleName(), id, version, rowCount);

        } else {
            log.warn("update {}[{},{}] by version, row count={}, failed.",
                    updateRecord.getClass().getSimpleName(), id, version, rowCount);
        }
        return sucFlag;

    }

    /**
     * update by version (not include isDeletedFlag)
     *
     * @param mapper
     * @param updateRecord
     * @param targetRecords
     * @param <T>
     * @return
     */
    public static <T> boolean updateByVersion(Mapper<T> mapper, T updateRecord, List<T> targetRecords) {
        return updateByVersion(mapper, updateRecord, targetRecords, false);
    }

    /**
     * update list by version
     *
     * @param mapper
     * @param updateRecord
     * @param targetRecords
     * @param <T>
     * @return
     */
    public static <T> boolean updateByVersion(Mapper<T> mapper, T updateRecord, List<T> targetRecords, boolean isDeletedFlag) {
        Precondition.checkNotNull(mapper);
        Precondition.checkNotNull(updateRecord);
        Precondition.checkNotEmpty(targetRecords, "targetRecords不能为空");

        // check first
        for (T targetRecord : targetRecords) {
            Object idObj = ReflectUtil.read(targetRecord, ModelConst.ID);
            Precondition.checkNotNull(idObj, "id不能为空");

            Object versionObj = ReflectUtil.read(targetRecord, ModelConst.VERSION);
            Precondition.checkNotNull(versionObj, "version不能为空");

            Example example = new Example(updateRecord.getClass());
            Example.Criteria criteria = example.createCriteria();
            ExampleUtil.set(criteria, ModelConst.ID, idObj);
            ExampleUtil.set(criteria, ModelConst.VERSION, versionObj);
            if (isDeletedFlag) {
                ExampleUtil.setIsDeletedFlag(example);
            }

            int rowCount = mapper.updateByExampleSelective(updateRecord, example);
            if (rowCount > 0) {
                log.info("update {}[id={},version={}] by version, row count={}",
                        updateRecord.getClass().getSimpleName(), idObj, versionObj, rowCount);
            } else {
                log.warn("fail to update {}[id={},version={}] by version, row count={}",
                        updateRecord.getClass().getSimpleName(), idObj, versionObj, rowCount);
                return false;
            }
        }

        return true;
    }


    /**
     * <p>this just sugar.</p>
     * the same to baseUpdateSelectiveByCode
     *
     * @param record
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    public static <T> int baseDeleteByCode(T record, Mapper<T> mapper, String code) {
        return baseUpdateSelectiveByCode(record, mapper, code);
    }

    /**
     * <p>this just sugar.</p>
     * the same to baseUpdateSelective.
     *
     * @param record
     * @param mapper
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> int baseDelete(T record, Mapper<T> mapper, String key, String value) {
        return baseUpdateSelective(record, mapper, key, value);
    }

    /**
     * 分页删除数据
     *
     * @param clazz
     * @param mapper
     * @param example
     * @param <T>
     * @return
     */
    public static <T> boolean delete(Class<?> clazz, Mapper<T> mapper, Example example) {
        return delete(clazz, mapper, example, DEFAULT_DELETE_PAGE_SIZE);
    }

    /**
     * 分页删除数据
     *
     * @param clazz
     * @param mapper
     * @param example
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> boolean delete(Class<?> clazz, Mapper<T> mapper, Example example, int pageSize) {
        if (clazz == null || mapper == null || example == null) {
            log.warn("clazz or mapper or example is null");
            return false;
        }
        int count = mapper.selectCountByExample(example);
        log.info("to be delete {} record count={}", clazz.getSimpleName(), count);

        if (count <= 0) {
            return true;
        }

        if (pageSize <= 0) {
            pageSize = DEFAULT_DELETE_PAGE_SIZE;
        }

        // lets delete them.
        example.selectProperties("id");
        int loopCount = 0;
        boolean hasNext = true;
        while (hasNext) {
            loopCount++;
            log.info("{} loop count={}", clazz.getSimpleName(), loopCount);

            PageHelper.startPage(1, pageSize, false);
            List<T> records = mapper.selectByExample(example);
            if (ListUtil.isEmpty(records)) {
                log.info("{} records is empty, so end.", clazz.getSimpleName());
                hasNext = false;
                break;
            }
            if (records.size() < pageSize) {
                log.info("{} records size {} < {}, so will end loop", clazz.getSimpleName(), records.size(), pageSize);
                hasNext = false;
            }
            List<Long> ids = new ArrayList<>();

            for (T record : records) {
                Long id = ReflectUtil.readAsLong(record, "id");
                if (id == null) {
                    continue;
                }
                ids.add(id);
            }
            if (ListUtil.isEmpty(ids)) {
                log.warn("delete {} ids is empty, plz check.", clazz.getSimpleName());
                return true;
            }

            Example deleteExample = new Example(clazz);
            Example.Criteria deleteCriteria = deleteExample.createCriteria();
            ExampleUtil.set(deleteCriteria, "id", ids);
            int rowCount = mapper.deleteByExample(deleteExample);
            log.info("delete {} count={}", clazz.getSimpleName(), rowCount);
        }

        return true;
    }

    /**
     * to page row bounds
     *
     * @param pageInfo
     * @return
     */
    public static RowBounds toPage(PageInfo pageInfo) {
        PageUtil.check(pageInfo);
        return new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
    }

}
