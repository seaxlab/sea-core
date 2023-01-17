package com.github.seaxlab.core.spring.component.extension;

import java.util.HashMap;
import java.util.Map;

/**
 * ExtensionRepository
 *
 * @author fulan.zjf 2017-11-05
 */
public class ExtensionRepository {

  public Map<ExtensionCoordinate, IExtensionPoint> getExtensionRepo() {
    return extensionRepo;
  }

  private Map<ExtensionCoordinate, IExtensionPoint> extensionRepo = new HashMap<>();


}
