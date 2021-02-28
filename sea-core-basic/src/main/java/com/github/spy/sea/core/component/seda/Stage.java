package com.github.spy.sea.core.component.seda;

import com.github.spy.sea.core.component.seda.event.EventHandler;

/**
 * Stage
 *
 * @author spy
 * @version 1.0 2020/11/27
 * @since 1.0
 */
public interface Stage {

    String getId();

    String getContext();

    EventHandler<?> getEventHandler();

    void setEventHandler(EventHandler<?> eventHandler);

    StageController getController();

}
