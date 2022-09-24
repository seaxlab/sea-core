package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Properties;

/**
 * object util
 *
 * @author spy
 * @version 1.0 2019-06-14
 * @since 1.0
 */
@Slf4j
public final class ObjectUtil {

    private ObjectUtil() {
    }

    private static final Object EMPTY = new Object();

    public static Object empty() {
        return EMPTY;
    }

    /**
     * 取值
     *
     * @param obj
     * @param field
     * @return
     */
    public static Object get(Object obj, String field) {
        if (obj == null) {
            return null;
        }

        return ReflectUtil.read(obj, field);
    }

    /**
     * check is null
     *
     * @param obj object
     * @return boolean
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * check object is not null
     *
     * @param obj object
     * @return boolean
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * 全部为空
     *
     * @param objects
     * @return
     */
    public static boolean isAllNull(Object... objects) {
        for (Object obj : objects) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 不全部为空
     *
     * @param objects
     * @return
     */
    public static boolean isNotAllNull(Object... objects) {
        for (Object obj : objects) {
            if (obj != null) {
                // 有值，不全部为空
                return true;
            }
        }
        // 全部为空
        return false;
    }

    /**
     * check all objects are empty.
     * 支持字符串、对象、集合混合检测
     *
     * @param objects
     * @return
     */
    public static boolean isAllEmpty(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                continue;
            }
            if (obj instanceof String) {
                String value = (String) obj;
                if (StringUtil.isNotEmpty(value)) {
                    return false;
                }
            } else if (obj instanceof Collection) {
                Collection collection = (Collection) obj;
                if (!collection.isEmpty()) {
                    return false;
                }
            } else if (ArrayUtil.isArray(obj)) {
                if (ArrayUtil.length(obj) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check has one object is null.
     *
     * @param objects
     * @return
     */
    public static boolean hasNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * check has one empty
     *
     * @param objects
     * @return
     */
    public static boolean hasEmpty(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
            if (obj instanceof String) {
                String value = (String) obj;
                if (StringUtil.isEmpty(value)) {
                    return true;
                }
            } else if (obj instanceof Collection) {
                Collection collection = (Collection) obj;
                if (collection.isEmpty()) {
                    return true;
                }
            } else if (ArrayUtil.isArray(obj)) {
                if (ArrayUtil.length(obj) == 0) {
                    return true;
                }
            }
        }
        //
        return false;
    }


    public static <T extends Comparable<? super T>> T min(final T... values) {
        return ObjectUtils.min(values);
    }

    public static <T extends Comparable<? super T>> T max(final T... values) {
        return ObjectUtils.min(values);
    }


    /**
     * 如果value为空，则返回defaultValue.
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static <T> T defaultIfNull(T value, T defaultValue) {
//        Preconditions.checkNotNull(defaultValue, "defaultValue can not be null");
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    /**
     * copy properties to object instance.
     *
     * @param p      properties
     * @param object object
     */
    public static void properties2Object(final Properties p, final Object object) {
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            String mn = method.getName();
            if (mn.startsWith("set")) {
                try {
                    String tmp = mn.substring(4);
                    String first = mn.substring(3, 4);

                    String key = first.toLowerCase() + tmp;
                    String property = p.getProperty(key);
                    if (property != null) {
                        Class<?>[] pt = method.getParameterTypes();
                        if (pt != null && pt.length > 0) {
                            String cn = pt[0].getSimpleName();
                            Object arg = null;
                            if (cn.equals("int") || cn.equals("Integer")) {
                                arg = Integer.parseInt(property);
                            } else if (cn.equals("long") || cn.equals("Long")) {
                                arg = Long.parseLong(property);
                            } else if (cn.equals("double") || cn.equals("Double")) {
                                arg = Double.parseDouble(property);
                            } else if (cn.equals("boolean") || cn.equals("Boolean")) {
                                arg = Boolean.parseBoolean(property);
                            } else if (cn.equals("float") || cn.equals("Float")) {
                                arg = Float.parseFloat(property);
                            } else if (cn.equals("String")) {
                                arg = property;
                            } else {
                                continue;
                            }
                            method.invoke(object, arg);
                        }
                    }
                } catch (Exception e) {
                    log.error("fail to copy properties to object ");
                }
            }
        }
    }

    public static void truncateStr(Object obj, String field, int maxLength) {
        if (obj == null) {
            return;
        }
        if (StringUtil.isBlank(field)) {
            return;
        }

        try {
            String remark = ReflectUtil.readAsString(obj, field);
            if (StringUtil.isNotBlank(remark) && remark.length() > maxLength) {
                String newRemark = StringUtil.left(remark, maxLength);
                ReflectUtil.write(obj, field, newRemark);
            }
        } catch (Exception e) {
            log.error("fail to truncate remark.", e);
        }
    }
}
