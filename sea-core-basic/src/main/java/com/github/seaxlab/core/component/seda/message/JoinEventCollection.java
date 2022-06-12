package com.github.seaxlab.core.component.seda.message;

import com.github.seaxlab.core.component.seda.Message;
import com.github.seaxlab.core.component.seda.event.Event;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class JoinEventCollection implements EventCollection {

    protected final Collection<Event> events;
    protected final Message targetMessage;

    public JoinEventCollection(Message targetMessage, Collection<Event> events) {
        if (events != null && !events.isEmpty()) {
            this.events = Collections.unmodifiableCollection(new LinkedList<Event>(events));
        } else {
            this.events = null;
        }
        this.targetMessage = targetMessage;
    }

    public Collection<Event> getEvents() {
        return this.events;
    }

    public Message getTargetMessage() {
        return this.targetMessage;
    }

}
