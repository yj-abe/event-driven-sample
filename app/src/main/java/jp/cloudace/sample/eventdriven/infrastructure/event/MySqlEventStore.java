package jp.cloudace.sample.eventdriven.infrastructure.event;

import java.util.Optional;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.application.event.EventStore;
import jp.cloudace.sample.eventdriven.application.event.StoredEvent;
import jp.cloudace.sample.eventdriven.domain.event.DomainEvent;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.EventDao;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.entity.EventDto;

public class MySqlEventStore implements EventStore {

    private final EventDao eventDao;
    private final EventTranslator translator;

    @Inject
    public MySqlEventStore(EventDao eventDao, EventTranslator translator) {
        this.eventDao = eventDao;
        this.translator = translator;
    }

    @Override
    public <T extends DomainEvent> StoredEvent<T> save(StoredEvent<T> event) {
        EventDto dto = translator.toDto(event);
        eventDao.insert(dto);
        event.setId(dto.getId());
        return event;
    }

    @Override
    public <T extends DomainEvent> Optional<StoredEvent<T>> fetchById(long id) {
        return eventDao.selectById(id).map(translator::toModel);
    }

    @Override
    public void delete(StoredEvent<?> event) {
        eventDao.delete(translator.toDto(event));
    }

}
