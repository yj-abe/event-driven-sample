package jp.cloudace.sample.eventdriven.infrastructure.persistence.translation;

import java.sql.Date;
import java.sql.Timestamp;

import jp.cloudace.sample.eventdriven.domain.common.Entity;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.entity.BaseDto;

public abstract class BaseTranslator<M extends Entity, D extends BaseDto>
        implements DataTranslator<M, D> {

    protected void attachToDto(M model, D dto) {
        dto.setCreatedAt(new Timestamp(model.getCreatedAt().getTime()));
        dto.setUpdatedAt(new Timestamp(model.getUpdatedAt().getTime()));
    }

    protected void attachToModel(D dto, M model) {
        model.setCreatedAt(new Date(dto.getCreatedAt().getTime()));
        model.setUpdatedAt(new Date(dto.getUpdatedAt().getTime()));
    }

}
