package com.github.seaxlab.core.component.template;

/**
 * route service
 *
 * @author spy
 * @version 1.0 2023/04/18
 * @since 1.0
 */
public interface RouteService<I, R> {

  R route(I input);

}
