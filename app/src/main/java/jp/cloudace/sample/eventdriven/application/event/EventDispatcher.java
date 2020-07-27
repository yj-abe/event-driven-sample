package jp.cloudace.sample.eventdriven.application.event;

import jp.cloudace.sample.eventdriven.domain.event.DomainEvent;

public interface EventDispatcher {

    void dispatchToProxy(long eventId);

    void dispatch(long eventId, DomainEvent event);

}
