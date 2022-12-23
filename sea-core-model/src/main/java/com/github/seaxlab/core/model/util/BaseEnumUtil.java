package com.github.seaxlab.core.model.util;

import com.github.seaxlab.core.enums.IBaseEnum;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Base Enum util
 *
 * @author spy
 * @version 1.0 2022/8/13
 * @since 1.0
 */
@Slf4j
public final class BaseEnumUtil {

  private BaseEnumUtil() {
  }

  /**
   * get code
   *
   * @param typeEnum
   * @return
   */
  public static <T> T getCode(IBaseEnum<T> typeEnum) {
    if (typeEnum == null) {
      log.warn("type enum is null");
      return null;
    }
    return typeEnum.getCode();
  }

  /**
   * get desc
   *
   * @param typeEnum
   * @return
   */
  public static <T> String getDesc(IBaseEnum<T> typeEnum) {
    if (typeEnum == null) {
      log.warn("type enum is null");
      return null;
    }
    return typeEnum.getDesc();
  }

  /**
   * to enum
   *
   * @param code
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T extends IBaseEnum<Integer>> Optional<T> toEnum(int code, Class<T> clazz) {
    return Arrays.stream(clazz.getEnumConstants()) //
      .filter(baseEnum -> baseEnum.getCode().intValue() == code) //
      .findAny();
  }

  /**
   * to enum
   *
   * @param code
   * @param clazz
   * @param defaultValue
   * @param <T>
   * @return
   */
  public static <T extends IBaseEnum<Integer>> T toEnum(int code, Class<T> clazz, T defaultValue) {
    return Arrays.stream(clazz.getEnumConstants()) //
      .filter(baseEnum -> baseEnum.getCode().intValue() == code) //
      .findAny() //
      .orElse(defaultValue);
  }

  /**
   * to enum
   *
   * @param code
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T extends IBaseEnum<String>> Optional<T> toEnum(String code, Class<T> clazz) {
    return Arrays.stream(clazz.getEnumConstants()) //
      .filter(baseEnum -> baseEnum.getCode().equalsIgnoreCase(code)) //
      .findAny();
  }

  /**
   * to enum
   *
   * @param code
   * @param clazz
   * @param defaultValue
   * @param <T>
   * @return
   */
  public static <T extends IBaseEnum<String>> T toEnum(String code, Class<T> clazz, T defaultValue) {
    return Arrays.stream(clazz.getEnumConstants()) //
      .filter(baseEnum -> baseEnum.getCode().equalsIgnoreCase(code)) //
      .findAny() //
      .orElse(defaultValue);
  }

}
