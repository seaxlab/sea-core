package com.github.seaxlab.core.component;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.seda.Dispatcher;
import com.github.seaxlab.core.component.seda.Message;
import com.github.seaxlab.core.component.seda.Stage;
import com.github.seaxlab.core.component.seda.dispatcher.DefaultDispatcher;
import com.github.seaxlab.core.component.seda.event.EventHandler;
import com.github.seaxlab.core.component.seda.stage.DefaultStage;
import com.github.seaxlab.core.component.seda.stage.RoutingOutcome;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/27
 * @since 1.0
 */
@Slf4j
public class SedaTest extends BaseCoreTest {

  Dispatcher dispatcher;

  @Override
  public void before() throws Exception {
    super.before();
    this.dispatcher = new DefaultDispatcher();
    List<Stage> stages = new LinkedList<Stage>();
    stages.add(this.createFirstStepMessage());
    stages.add(this.createSecondStepMessage());
    this.dispatcher.setStages(stages);
    this.dispatcher.setContext("Test-" + Long.toHexString(UUID.randomUUID().toString().hashCode()));
    this.dispatcher.start();
  }

  @Test
  public void run17() throws Exception {
    this.dispatcher.execute("FIRST", new StageFirstMessage("FIRST-Message"));
  }

  /**
   * Basic multiple flow processing test
   *
   * @throws Throwable
   */
  @Test
  public void testStraightMultipleFlow() throws Throwable {
    log.info("START: testStraightMultipleFlow >>>>>>>>>>>>>>>>>>>>>>>");
    StageFirstMessage msg = new StageFirstMessage("MULTI-FIRST-Message");
    msg.setCount(10);
    this.dispatcher.execute("FIRST", msg);
  }

  /**
   * Heavy multiple flow processing test
   *
   * @throws Throwable
   */
  @Test
  public void testHeavyStraightMultipleFlow() throws Throwable {
    log.info("START: testStraightMultipleFlow >>>>>>>>>>>>>>>>>>>>>>>");
    int iterations = 200;
    for (int i = 0; i < iterations; i++) {
      StageFirstMessage data = new StageFirstMessage("MULTI-FIRST-Message");
      data.setCount(10);
      this.dispatcher.execute("FIRST", data);
    }
  }


  @After
  public void after() throws Exception {
    TimeUnit.MINUTES.sleep(1);
    this.dispatcher.stop();
  }

  private Stage createFirstStepMessage() {
    Stage stage = new DefaultStage("FIRST", "TEST");
    stage.setEventHandler((EventHandler<StageFirstMessage>) data -> {
      StageFirstMessage sfe = data;
      log.info(">> First Stage Command: " + sfe.getValue());
      RoutingOutcome output = RoutingOutcome.create();
      for (int i = 0; i < sfe.getCount(); i++) {
        output.add("SECOND", new StageSecondMessage("SECOND-Message:" + i));
      }
      return output;
    });
    return stage;
  }

  private Stage createSecondStepMessage() {
    Stage stage = new DefaultStage("SECOND", "TEST");
    stage.setEventHandler((EventHandler<StageSecondMessage>) data -> {
      log.info(">> Second Stage Command: " + (data).getValue());
      return null;
    });
    return stage;
  }

  private static class StageFirstMessage implements Message {
    private final String value;
    private int count = 1;

    public StageFirstMessage(String value) {
      this.value = value;
    }

    public int getCount() {
      return this.count;
    }

    public void setCount(int count) {
      this.count = count;
    }

    public String getValue() {
      return this.value;
    }
  }

  private static class StageSecondMessage implements Message {
    private final String value;

    public StageSecondMessage(String value) {
      this.value = value;
    }

    public String getValue() {
      return this.value;
    }
  }
}
