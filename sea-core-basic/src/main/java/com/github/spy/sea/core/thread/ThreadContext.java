package com.github.spy.sea.core.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.SetUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 线程上下文
 *
 * @author spy
 * @version 1.0 2019-05-14
 * @since 1.0
 */
@Slf4j
public class ThreadContext {

    private ThreadContext() {
    }

    /**
     * 线程上下文变量的持有者
     */
    private static final ThreadLocal<Map<String, Object>> CTX_HOLDER = ThreadLocal.withInitial(() -> new HashMap<>());

    /**
     * 添加内容到线程上下文中
     *
     * @param key
     * @param value
     */
    public static final void put(String key, Object value) {
        init();
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return;
        }
        ctx.put(key, value);
    }

    /**
     * put if absent
     *
     * @param key
     * @param value
     */
    public static final void putIfAbsent(String key, Object value) {
        init();
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return;
        }
        ctx.putIfAbsent(key, value);
    }

    //compute（相当于put,只不过返回的是新值）

    /**
     * 从线程上下文中获取内容
     *
     * @param key
     */
    public static final <T extends Object> T get(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return (T) ctx.get(key);
    }

    /**
     * get value, if null return default value.
     *
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static final <T extends Object> T get(String key, T defaultValue) {
        T v = get(key);
        return v == null ? defaultValue : v;
    }

    /**
     * get safe from context
     *
     * @param key
     * @param <T>
     * @return
     */
    public static final <T extends Object> T getSafe(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        try {
            return (T) ctx.get(key);
        } catch (Exception e) {
            log.warn("convert to T error", e);
            return null;
        }
    }

    /**
     * get safe from context, if null return default value.
     *
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static final <T extends Object> T getSafe(String key, T defaultValue) {
        T v = getSafe(key);
        return v == null ? defaultValue : v;
    }

    /**
     * 获取mock配置，默认返回false
     *
     * @param mockKey
     * @return
     */
    public static final boolean getMockFlag(String mockKey) {
        if (mockKey == null || mockKey.isEmpty()) {
            return false;
        }
        if (mockKey.startsWith("sea.mock.")) {
            mockKey = mockKey.replace("sea.mock.", "");
        }
        String value = getSafe(CoreConst.DEFAULT_MOCK_KEY);
        if (StringUtil.isNotEmpty(value)) {
            if (value.contains(",")) {
                Set<String> keySet = SetUtil.toSet(value.split("\\,"));
                return keySet.contains(mockKey);
            } else {
                return EqualUtil.isEq(mockKey, value, false);
            }
        }

        return false;
    }

    /**
     * get all mock data
     *
     * @return
     */
    public static final String getMockData() {
        String value = getSafe(CoreConst.DEFAULT_MOCK_KEY);
        return StringUtil.defaultIfBlank(value, StringUtil.EMPTY);
    }

    /**
     * convert mock data to json.
     *
     * @return
     */
    public static final JSONObject getMockDataJSON() {
        String value = getSafe(CoreConst.DEFAULT_MOCK_KEY);
        if (StringUtil.isNotEmpty(value)) {
            if (JSON.isValidObject(value)) {
                return JSON.parseObject(value);
            } else {
                log.warn("mock data is not valid json object.");
                return new JSONObject();
            }
        }

        return new JSONObject();
    }


    /**
     * 取json格式中的值
     *
     * @param mockDataKey mock data key in json
     * @return
     */
    public static final String getMockDataStr(String mockDataKey) {
        if (mockDataKey == null || mockDataKey.isEmpty()) {
            return StringUtil.EMPTY;
        }
        if (mockDataKey.startsWith("sea.mock.")) {
            mockDataKey = mockDataKey.replace("sea.mock.", "");
        }
        String value = getSafe(CoreConst.DEFAULT_MOCK_KEY);
        if (StringUtil.isNotEmpty(value)) {
            if (JSON.isValidObject(value)) {
                JSONObject jsonObj = JSONObject.parseObject(value);
                return jsonObj.getString(mockDataKey);
            } else {
                log.warn("sea mock data is not valid json object.");
                return StringUtil.EMPTY;
            }
        }
        return StringUtil.EMPTY;
    }

    /**
     * 获取mockdata
     *
     * @param mockDataKey
     * @return
     */
    public static final boolean getMockDataBool(String mockDataKey) {
        if (mockDataKey == null || mockDataKey.isEmpty()) {
            return false;
        }
        if (mockDataKey.startsWith("sea.mock.")) {
            mockDataKey = mockDataKey.replace("sea.mock.", "");
        }
        String value = getSafe(CoreConst.DEFAULT_MOCK_KEY);
        if (StringUtil.isNotEmpty(value)) {
            if (JSON.isValidObject(value)) {
                JSONObject jsonObj = JSONObject.parseObject(value);
                return jsonObj.getBooleanValue(mockDataKey);
            } else {
                log.warn("mock data is not valida json object.");
                return false;
            }
        }
        return false;
    }


    /**
     * 获取线程上下文
     */
    public static final Map<String, Object> getContext() {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return ctx;
    }

    /**
     * 删除上下文中的key
     *
     * @param key
     */
    public static final void remove(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            ctx.remove(key);
        }
    }

    /**
     * 上下文中是否包含此key
     *
     * @param key
     * @return
     */
    public static final boolean contains(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            return ctx.containsKey(key);
        }
        return false;
    }

    /**
     * 清空线程上下文
     */
    public static final void clean() {
        if (log.isDebugEnabled()) {
            log.debug("thread context clean");
        }

        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            ctx.clear();
        }
        CTX_HOLDER.remove();
    }

    /**
     * 初始化线程上下文
     */
    public static final void init() {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            CTX_HOLDER.set(new HashMap<>());
        }
    }

}
