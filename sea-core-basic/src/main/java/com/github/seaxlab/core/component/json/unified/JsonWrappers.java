package com.github.seaxlab.core.component.json.unified;

import static java.util.stream.Collectors.toList;

import com.github.seaxlab.core.loader.EnhancedServiceLoader;
import com.github.seaxlab.core.util.EqualUtil;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/**
 * json wrappers util
 *
 * @author spy
 * @version 1.0 2023/09/22
 * @since 1.0
 */
@Slf4j
public final class JsonWrappers {

  private static final List<JsonWrapper> WRAPPERS = Lists.newArrayList();

  private JsonWrappers() {
    List<JsonWrapper> loadedWrappers = EnhancedServiceLoader.loadAll(JsonWrapper.class);
    if (CollectionUtils.isNotEmpty(loadedWrappers)) {
      WRAPPERS.addAll(loadedWrappers);
    }

//    WRAPPERS.add(new TtlTaskWrapper());
//    WRAPPERS.add(new MdcTaskWrapper());
  }

  public List<JsonWrapper> getByNames(Set<String> names) {
    if (CollectionUtils.isEmpty(names)) {
      return Collections.emptyList();
    }

    return WRAPPERS.stream().filter(t -> EqualUtil.isIn(t.name(), names)).collect(toList());
  }

  public static JsonWrappers getInstance() {
    return WrappersHolder.INSTANCE;
  }

  private static class WrappersHolder {

    private static final JsonWrappers INSTANCE = new JsonWrappers();
  }

}
