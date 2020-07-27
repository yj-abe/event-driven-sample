package jp.cloudace.sample.eventdriven.application.event;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.application.persistence.Transactional;
import jp.cloudace.sample.eventdriven.domain.event.DomainEvent;

public class EventRelay {

    private final EventQueue queue;
    private final EventDispatcher dispatcher;

    @Inject
    public EventRelay(EventQueue queue, EventDispatcher dispatcher) {
        this.queue = queue;
        this.dispatcher = dispatcher;
    }

    @Transactional
    public void relay(long eventId) {
        DomainEvent event = queue.pop(eventId)
                .orElseThrow(() -> new IllegalStateException("Event is not yet ensured or lost."));
        dispatcher.dispatch(eventId, event);
    }

}
