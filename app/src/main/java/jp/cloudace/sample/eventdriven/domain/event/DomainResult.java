package jp.cloudace.sample.eventdriven.domain.event;

import lombok.Value;

@Value
public class DomainResult<R, E extends DomainEvent> {
    R result;
    E event;
}
