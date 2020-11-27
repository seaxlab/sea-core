package com.github.spy.sea.core.function.seda.message;

import com.github.spy.sea.core.function.seda.Dispatcher;
import com.github.spy.sea.core.function.seda.Message;
import com.github.spy.sea.core.function.seda.event.Event;
import com.github.spy.sea.core.function.seda.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

public class JoinHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JoinHandler.class);
    private static final boolean IS_DEBUG_ENABLED = LOGGER.isDebugEnabled();

    private final Dispatcher dispatcher;
    private final Event targetEvent;
    private final AtomicLong expectedEvents = new AtomicLong(0);
    private final AtomicLong finishedEvents = new AtomicLong(0);
    private final Collection<Event> collectedEvents;

    public JoinHandler(Dispatcher dispatcher, Event targetEvent) {
        this.dispatcher = dispatcher;
        this.targetEvent = targetEvent;
        this.collectedEvents = new LinkedList<Event>();
    }

    public Event getTargetEvent() {
        return this.targetEvent;
    }

    public void register(EventHandler<?> source) {
        this.expectedEvents.incrementAndGet();
    }

    public void finished(Event event, Message returnMessage) {
        String stage = event.getStage();
        if (returnMessage != null) {
            synchronized (this.collectedEvents) {
                this.collectedEvents.add(new Event(stage, returnMessage));
            }
        }

        long currentFinishedEvent = this.finishedEvents.incrementAndGet();
        long currentExpectedEvent = this.expectedEvents.get();

        if (currentFinishedEvent == currentExpectedEvent) {
            JoinEventCollection joinEventCollection = new JoinEventCollection(this.targetEvent.getMessage(),
                    this.collectedEvents);
            Event joinEvent = new Event(this.targetEvent.getStage(), joinEventCollection);

            if (IS_DEBUG_ENABLED) {
                LOGGER.debug("executing join event: " + joinEvent);
            }
            this.dispatcher.execute(joinEvent);
        }
    }
}
