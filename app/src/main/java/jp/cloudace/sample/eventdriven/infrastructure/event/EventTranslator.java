package jp.cloudace.sample.eventdriven.infrastructure.event;

import java.sql.Timestamp;
import java.util.Date;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.application.event.StoredEvent;
import jp.cloudace.sample.eventdriven.domain.event.DomainEvent;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.entity.EventDto;

public class EventTranslator {

    private final DomainEventSerializer serializer;

    @Inject
    public EventTranslator(DomainEventSerializer serializer) {
        this.serializer = serializer;
    }

    public <T extends DomainEvent> StoredEvent<T> toModel(EventDto dto) {
        T event = serializer.deserialize(dto.getEvent(), dto.getEventName());
        return new StoredEvent<>(
                dto.getId(),
                event,
                dto.getEventName(),
                new Date(dto.getStoredAt().getTime()),
                dto.getVersion()
        );
    }

    public EventDto toDto(StoredEvent<?> storedEvent) {
        EventDto dto = new EventDto();
        storedEvent.getId().ifPresent(dto::setId);
        dto.setEvent(serializer.serialize(storedEvent.getEvent()));
        dto.setEventName(storedEvent.getEventName());
        dto.setStoredAt(new Timestamp(storedEvent.getStoredAt().getTime()));
        dto.setVersion(storedEvent.getVersion());
        return dto;
    }

}
