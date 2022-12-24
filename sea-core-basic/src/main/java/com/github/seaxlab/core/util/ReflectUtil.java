package com.github.seaxlab.core.util;

import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.model.Result;
import com.google.common.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * reflect util
 *
 * @author spy
 * @version 1.0 2019-06-14
 * @since 1.0
 */
@Slf4j
public final class ReflectUtil {

  private ReflectUtil() {
  }


  /**
   * get all fields list.
   *
   * @param cls
   * @return
   */
  public static List<Field> getAllFieldsList(final Class<?> cls) {
    return FieldUtils.getAllFieldsList(cls);
  }

  /**
   * force access field.
   *
   * @param clz
   * @param fieldName
   * @return
   */
  public static Field getField(final Class<?> clz, String fieldName) {
    return FieldUtils.getField(clz, fieldName, true);
  }

  /**
   * read static field value
   *
   * @param clz
   * @param fieldName
   * @return
   */
  public static Object readStatic(final Class<?> clz, String fieldName) {
    try {
      return FieldUtils.readStaticField(clz, fieldName, true);
    } catch (Exception e) {
      log.error("fail to read static field", e);
    }
    return null;
  }

  /**
   * read field value from target
   *
   * @param target
   * @param fieldName
   * @return
   */
  public static Object read(final Object target, final String fieldName) {
    try {
      return FieldUtils.readField(target, fieldName, true);
    } catch (Exception e) {
      log.error("fail to read field name", e);
    }
    return null;
  }

  /**
   * 取String
   *
   * @param target
   * @param fieldName
   * @return
   */
  public static String readAsString(final Object target, final String fieldName) {
    Object value = read(target, fieldName);

    return value == null ? null : value.toString();
  }

  /**
   * get As Integer.
   *
   * @param target
   * @param fieldName
   * @return
   */
  public static Integer readAsInteger(final Object target, final String fieldName) {
    Object value = read(target, fieldName);

    if (value != null) {
      try {
        return (Integer) value;
      } catch (Exception e) {
        log.error("fail to convert to Integer", e);
      }
    }
    return null;
  }

  /**
   * get As Long.
   *
   * @param target
   * @param fieldName
   * @return
   */
  public static Long readAsLong(final Object target, final String fieldName) {
    Object value = read(target, fieldName);

    if (value != null) {
      try {
        return (Long) value;
      } catch (Exception e) {
        log.error("fail to convert to Long", e);
      }
    }
    return null;
  }


  /**
   * 赋值
   *
   * @param target    ''
   * @param fieldName ''
   * @param value     ''
   */
  public static void write(final Object target, final String fieldName, final Object value) {
    try {
      Field field = FieldUtils.getField(target.getClass(), fieldName, true);

      if (field == null) {
        log.warn("{} has no field {}", ClassUtil.getClassName(target.getClass()), fieldName);
        return;
      }

      Object finalValue = simpleConvertValue(field, value);

      FieldUtils.writeField(target, fieldName, finalValue, true);
    } catch (Exception e) {
      log.error("fail to write value", e);
    }
  }

  private static final Pattern GET_PATTERN = Pattern.compile("^get[A-Z].*");
  private static final Pattern IS_PATTERN = Pattern.compile("^is[A-Z].*");

  /**
   * get field name of class from function
   * <note>
   * T is Object class, field should be Object class , not primitive type(such as int/long/dubbo and so on).
   * </note>
   *
   * @param fn custom function serialize
   * @return
   */
  public static <T> String getFieldName(Fn<T, Object> fn) {
    try {
      Method method = fn.getClass().getDeclaredMethod("writeReplace");
      method.setAccessible(Boolean.TRUE);
      SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
      String getter = serializedLambda.getImplMethodName();
      if (GET_PATTERN.matcher(getter).matches()) {
        getter = getter.substring(3);
      } else if (IS_PATTERN.matcher(getter).matches()) {
        getter = getter.substring(2);
      }
      return Introspector.decapitalize(getter);
    } catch (ReflectiveOperationException e) {
      log.error("reflective operation exception", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REFLECT_OPERATION_ERROR);
    }
    return null;
  }


  /**
   * Attempt to find a {@link Method} on the supplied class with the supplied name and parameter types. Searches all
   * superclasses up to {@code Object}.
   * <p>Returns {@code null} if no {@link Method} can be found.
   *
   * @param clazz      the class to introspect
   * @param name       the name of the method
   * @param paramTypes the parameter types of the method (may be {@code null} to indicate any signature)
   * @return the Method object, or {@code null} if none found
   */
  public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
    Class<?> searchType = clazz;
    while (searchType != null) {
      Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
      for (Method method : methods) {
        if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes,
          method.getParameterTypes()))) {
          return method;
        }
      }
      searchType = searchType.getSuperclass();
    }
    return null;
  }

  /**
   * invoke
   *
   * @param target
   * @param method
   * @param args
   * @return
   */
  public static Result<Object> invokeMethod(Object target, Method method, Object... args) {
    Result result = Result.fail();
    try {
      result.value(method.invoke(target, args));
    } catch (Exception ex) {
      log.error("fail to invoke method.", ex);
      result.setMsg(ex.getMessage());
    }
    return result;
  }

  /**
   * invoke method
   *
   * @param object
   * @param method
   * @return
   */
  public static Object invokeMethod(Object object, String method) {
    try {
      return MethodUtils.invokeMethod(object, method);
    } catch (Exception e) {
      log.error("invoke method error", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REFLECT_OPERATION_ERROR);
    }
    return null;
  }

  /**
   * invoke method
   *
   * @param object
   * @param forceAccess
   * @param method
   * @return
   */
  public static Object invokeMethod(Object object, boolean forceAccess, String method) {
    try {
      return MethodUtils.invokeMethod(object, forceAccess, method);
    } catch (Exception e) {
      log.error("invoke method error", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_REFLECT_OPERATION_ERROR);
    }
    return null;
  }

  /**
   * invoke method safely.
   *
   * @param object
   * @param method
   * @return
   */
  public static Result<Object> invokeMethodSafe(Object object, String method) {
    Result<Object> result = Result.fail();
    try {
      result.value(MethodUtils.invokeMethod(object, method));
    } catch (Exception e) {
      log.error("invoke method error", e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  /**
   * invoke method safely
   *
   * @param object
   * @param method
   * @param args
   * @return
   */
  public static Result<Object> invokeMethodSafe(Object object, String method, Object... args) {
    Result<Object> result = Result.fail();
    try {
      result.value(MethodUtils.invokeMethod(object, method, args));
    } catch (Exception e) {
      log.error("invoke method error", e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  /**
   * invoke method safely.
   *
   * @param object
   * @param forceAccess
   * @param method
   * @return
   */
  public static Result<Object> invokeMethodSafe(Object object, boolean forceAccess, String method) {
    Result<Object> result = Result.fail();

    try {
      result.value(MethodUtils.invokeMethod(object, forceAccess, method));
    } catch (Exception e) {
      log.error("invoke method error", e);
      result.setMsg(e.getMessage());
    }
    return null;
  }

  /**
   * invoke method safely.
   *
   * @param object
   * @param forceAccess
   * @param method
   * @param args
   * @return
   */
  public static Result<Object> invokeMethodSafe(Object object, boolean forceAccess, String method, Object... args) {
    Result<Object> result = Result.fail();

    try {
      result.value(MethodUtils.invokeMethod(object, forceAccess, method, args));
    } catch (Exception e) {
      log.error("invoke method error", e);
      result.setMsg(e.getMessage());
    }
    return null;
  }

  /**
   * invoke static method
   *
   * @param clazz  ''
   * @param method ''
   * @return
   */
  public static Result<Object> invokeStaticMethod(final Class<?> clazz, String method) {
    return invokeStaticMethod(clazz, method, null);
  }

  /**
   * invoke static method.
   *
   * @param clazz
   * @param method
   * @param args
   * @return
   */
  public static Result<Object> invokeStaticMethod(final Class<?> clazz, String method, Object... args) {
    Result<Object> result = Result.fail();
    try {
      result.value(MethodUtils.invokeStaticMethod(clazz, method, args));
    } catch (Exception e) {
      log.error("fail to invoke static method", e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  /**
   * 基本类型的进行转换，避免无意义的异常
   *
   * @param field
   * @param value
   * @return
   */
  private static Object simpleConvertValue(Field field, final Object value) {
    Object finalValue = value;
    if (field.getType().isAssignableFrom(String.class)) {

      if (value.getClass().isAssignableFrom(Long.class) || value.getClass().isAssignableFrom(Integer.class)) {
        finalValue = value.toString();
      }

    } else if (field.getType().isAssignableFrom(Long.class) || value.getClass().isAssignableFrom(Integer.class)) {

      if (value.getClass().isAssignableFrom(String.class)) {
        finalValue = Long.valueOf(value.toString());
      }
      if (value.getClass().isAssignableFrom(Integer.class)) {
        finalValue = Long.valueOf((Integer) value);
      }

    } else if (field.getType().isAssignableFrom(Integer.class)) {
      if (value.getClass().isAssignableFrom(String.class)) {
        finalValue = Integer.valueOf(value.toString());
      }
    }

    return finalValue;
  }


  /**
   * 获取单个泛型类
   * <p>
   * interface IUserService<T> { }
   * <p>
   * class UserServiceImpl implements IUserService<User> { } IUserService userService = new UserServiceImpl();
   * </p>
   *
   * @param obj
   * @return
   */
  public static Class<?> getSingleGenericClass(Object obj) {
    Type[] genericInterfaces = obj.getClass().getGenericInterfaces();

    if (genericInterfaces == null || genericInterfaces.length == 0) {
      return null;
    }

    Type first = genericInterfaces[0];
    if (first instanceof ParameterizedType) {
      Type[] genericTypes = ((ParameterizedType) first).getActualTypeArguments();
      for (Type genericType : genericTypes) {
        return TypeToken.of(genericType).getRawType();
      }
    }
    return null;
  }

  public static Class<?>[] getAllGenericClass(Object obj) {
    Type[] genericInterfaces = obj.getClass().getGenericInterfaces();

    if (genericInterfaces == null || genericInterfaces.length == 0) {
      return null;
    }
    List<Class<?>> data = new ArrayList<>();
    for (int i = 0; i < genericInterfaces.length; i++) {
      Type type = genericInterfaces[i];
      if (type instanceof ParameterizedType) {
        Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
        for (Type genericType : genericTypes) {
          data.add(TypeToken.of(genericType).getRawType());
        }
      }
    }

    return data.toArray(new Class<?>[data.size()]);
  }

  public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterType) {
    try {
      return clazz.getConstructor(parameterType);
    } catch (NoSuchMethodException e) {
      log.error("no such method.", e);
    }
    return null;
  }


  /**
   * check has default constructor.
   *
   * @param clazz
   * @return
   * @throws SecurityException
   */
  public static boolean hasDefaultConstructor(Class<?> clazz) throws SecurityException {
    Class<?>[] empty = {};
    try {
      clazz.getConstructor(empty);
    } catch (NoSuchMethodException e) {
      return false;
    }
    return true;
  }

  /**
   * check has spec field.
   *
   * @param clazz
   * @param fieldName
   * @return
   */
  public static boolean hasField(Class<?> clazz, String fieldName) {
    return hasField(clazz, fieldName, null);
  }


  /**
   * check has spec field.
   *
   * @param clazz
   * @param fieldName
   * @param fieldType
   * @return
   */
  public static boolean hasField(Class<?> clazz, String fieldName, Class<?> fieldType) {
    try {
      Field field = FieldUtils.getField(clazz, fieldName, true);
      if (field != null) {
        if (fieldType != null) {
          return fieldType.equals(field.getType());
        }
        return true;
      }
    } catch (Exception e) {
      log.error("fail to check field", e);
    }

    return false;
  }

  /**
   * check has spec method.
   *
   * @param clazz
   * @param methodName
   * @param parameterType
   * @return
   */
  public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... parameterType) {
    try {
      Method method = MethodUtils.getMatchingMethod(clazz, methodName, parameterType);
      return method != null;
    } catch (Exception e) {
      log.error("fail to check method.", e);
    }
    return false;
  }

  // public static class
  // 重点：interface is static

  /**
   * Custom Function that can get writeReplace method.
   *
   * @param <T>
   * @param <R>
   */
  public interface Fn<T, R> extends Function<T, R>, Serializable {

  }

}
