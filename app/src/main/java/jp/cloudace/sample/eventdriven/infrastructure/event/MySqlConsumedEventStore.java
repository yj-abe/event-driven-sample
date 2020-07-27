package jp.cloudace.sample.eventdriven.infrastructure.event;

import java.sql.Timestamp;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.ConsumedEventDao;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.entity.ConsumedEventDto;
import jp.cloudace.sample.eventdriven.presentation.event.ConsumedEvent;
import jp.cloudace.sample.eventdriven.presentation.event.ConsumedEventStore;

public class MySqlConsumedEventStore implements ConsumedEventStore {

    private final ConsumedEventDao consumedEventDao;

    @Inject
    public MySqlConsumedEventStore(ConsumedEventDao consumedEventDao) {
        this.consumedEventDao = consumedEventDao;
    }

    @Override
    public boolean exists(long eventId, String receiver) {
        return consumedEventDao.exists(eventId, receiver);
    }

    @Override
    public void insert(ConsumedEvent event) {
        ConsumedEventDto dto = new ConsumedEventDto();
        dto.setId(event.getEventId());
        dto.setReceiver(event.getReceiver());
        dto.setReceivedAt(new Timestamp(event.getReceivedAt().getTime()));
        consumedEventDao.insert(dto);
    }

}
