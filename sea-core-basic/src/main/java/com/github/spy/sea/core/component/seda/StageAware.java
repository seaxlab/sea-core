package com.github.spy.sea.core.component.seda;

/**
 * stage aware
 */
public interface StageAware {

    void setStage(Stage stage);

    Stage getStage();

}
