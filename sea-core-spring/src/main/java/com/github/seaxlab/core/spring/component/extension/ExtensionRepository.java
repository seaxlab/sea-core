package com.github.seaxlab.core.spring.component.extension;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * ExtensionRepository
 *
 * @author fulan.zjf 2017-11-05
 */
@Component("seaCoreExtensionRepository")
public class ExtensionRepository {

  public Map<ExtensionCoordinate, IExtensionPoint> getExtensionRepo() {
    return extensionRepo;
  }

  private Map<ExtensionCoordinate, IExtensionPoint> extensionRepo = new HashMap<>();


}
