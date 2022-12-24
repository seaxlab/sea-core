package com.github.seaxlab.core.pattern.plugin.support;

import com.github.seaxlab.core.pattern.plugin.Plugin;
import com.github.seaxlab.core.pattern.plugin.PluginChain;
import com.github.seaxlab.core.pattern.plugin.PluginContext;
import com.github.seaxlab.core.pattern.plugin.PluginHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/4
 * @since 1.0
 */
@Slf4j
public class DefaultPluginHandler implements PluginHandler {

  private List<Plugin> plugins;


  public DefaultPluginHandler(List<Plugin> plugins) {
    this.plugins = plugins;
    Collections.sort(this.plugins, (o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder()));
  }

  @Override
  public void handle(PluginContext context) {
    new DefaultPluginChain(plugins).execute(context);
  }


  private static class DefaultPluginChain implements PluginChain {

    private int index;

    private final List<Plugin> plugins;

    /**
     * Instantiates a new Default plugin chain.
     *
     * @param plugins the plugins
     */
    DefaultPluginChain(final List<Plugin> plugins) {
      this.plugins = plugins;
    }

    /**
     * Delegate to the next  in the chain.
     *
     * @param context plugin context
     */
    @Override
    public void execute(final PluginContext context) {
      if (this.index < plugins.size()) {
        Plugin plugin = plugins.get(this.index++);

        boolean skip = plugin.skip(context);
        if (skip) {
          this.execute(context);
          return;
        }

        plugin.execute(context, this);
      }
    }
  }
}
