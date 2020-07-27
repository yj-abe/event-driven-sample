package jp.cloudace.sample.eventdriven.presentation.event;

public interface ConsumedEventStore {
    boolean exists(long eventId, String receiver);
    void insert(ConsumedEvent event);
}
