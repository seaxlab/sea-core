package com.github.seaxlab.core.concurrent;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/25
 * @since 1.0
 */
@Slf4j
public class ActorSystemTest extends BaseCoreTest {

    // void pull(PullMessageProcessor.PullEntry pullEntry) {
    //        final String actorPath = ConsumerGroupUtils.buildConsumerGroupKey(pullEntry.subject, pullEntry.group);
    //        actorSystem.dispatch(actorPath, pullEntry, this);
    //    }
//    @Test
//    public void test15() throws Exception {
//        MyProcessor processor = new MyProcessor();
//        ActorSystem actor = new ActorSystem("test");
//
//        for (int i = 0; i < 10; i++) {
//            actor.dispatch("abc", "" + i, processor);
//        }
//
//        sleepMinute(1);
//    }
//
//    private class MyProcessor implements ActorSystem.Processor<String> {
//
//        @Override
//        public boolean process(String message, ActorSystem.Actor<String> self) {
//            log.info("message={}", message);
//
//            return true;
//        }
//    }
}
