package com.github.spy.sea.core.function.seda;

/**
 * stage aware
 */
public interface StageAware {

    void setStage(Stage stage);

    Stage getStage();

}
