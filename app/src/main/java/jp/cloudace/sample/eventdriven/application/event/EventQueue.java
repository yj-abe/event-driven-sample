package jp.cloudace.sample.eventdriven.application.event;

import java.util.Optional;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.domain.event.DomainEvent;

public class EventQueue {

    private final EventStore store;
    private final EventDispatcher dispatcher;

    @Inject
    public EventQueue(EventStore store, EventDispatcher dispatcher) {
        this.store = store;
        this.dispatcher = dispatcher;
    }

    public void push(DomainEvent event) {
        StoredEvent<DomainEvent> storedEvent = new StoredEvent<>(event);
        storedEvent = store.save(storedEvent);
        dispatcher.dispatchToProxy(storedEvent.getId()
                .orElseThrow(() -> new IllegalStateException("event id is null.")));
    }

    public <T extends DomainEvent> Optional<T> pop(long id) {
        return store.<T>fetchById(id).map(stored -> {
            store.delete(stored);
            return stored.getEvent();
        });
    }

}
