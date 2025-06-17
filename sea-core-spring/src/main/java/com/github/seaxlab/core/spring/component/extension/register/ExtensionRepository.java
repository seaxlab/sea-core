package com.github.seaxlab.core.spring.component.extension.register;

import com.github.seaxlab.core.spring.component.extension.IExtensionPoint;
import com.github.seaxlab.core.spring.component.extension.model.ExtensionCoordinate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ExtensionRepository
 *
 * @author fulan.zjf 2017-11-05
 */
public class ExtensionRepository {

  // 扩展点缓存
  private final Map<ExtensionCoordinate, IExtensionPoint> extensionRepo = new ConcurrentHashMap<>();


  public Map<ExtensionCoordinate, IExtensionPoint> getExtensionRepo() {
    return extensionRepo;
  }


}
