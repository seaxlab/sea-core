package com.github.seaxlab.core.example.servlet;

import javax.servlet.ServletContext;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/18
 * @since 1.0
 */
public interface MyContainerInitializer {

  void onStartup(ServletContext context);

}
