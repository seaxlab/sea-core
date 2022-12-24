package com.github.seaxlab.core.spring.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.InputStream;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/1/26
 * @since 1.0
 */
@Slf4j
public final class ResourceUtil {

  /**
   * 加载最外层文件
   * <p>key:parentDir + "/" + filename</p>
   *
   * @param pattern
   * @return
   */
  public static Map<String /*parentDir/filename"*/, InputStream> load(String pattern) {
    Map<String, InputStream> resourceMap = Maps.newHashMap();

    // 1. 定义资源匹配规则，会在所有的JAR包的根目录下搜索指定文件
    String matchPattern;
    if (pattern.startsWith(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
      matchPattern = pattern;
    } else {
      matchPattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + pattern;
    }

    // 2. 返回指定路径下所有的资源对象（子目录下的资源对象）
    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    try {
      Resource[] resources = resourcePatternResolver.getResources(matchPattern);
      for (Resource resource : resources) {
        // 4. 获取子目录名称
        String[] filePathAry = resource.getFile().getPath().split("/");
        String parentDir = filePathAry[filePathAry.length - 2];
        String fileName = filePathAry[filePathAry.length - 1];
        String key = parentDir + "/" + fileName;
        if (!resourceMap.containsKey(key)) {
          resourceMap.put(key, resource.getInputStream());
        }
      }
    } catch (Exception e) {
      log.error("fail to load resources", e);
    }

    return resourceMap;
  }

}
