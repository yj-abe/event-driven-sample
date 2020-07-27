package jp.cloudace.sample.eventdriven.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.domain.model.cloumn.Column;
import jp.cloudace.sample.eventdriven.domain.model.cloumn.ColumnId;
import jp.cloudace.sample.eventdriven.domain.model.cloumn.ColumnRepository;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.ColumnDao;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.translation.ColumnTranslator;

public class MySqlColumnRepository implements ColumnRepository {

    private final ColumnDao dao;
    private final ColumnTranslator translator;

    @Inject
    public MySqlColumnRepository(ColumnDao dao, ColumnTranslator translator) {
        this.dao = dao;
        this.translator = translator;
    }

    @Override
    public ColumnId newId() {
        return new ColumnId(UUID.randomUUID().toString());
    }

    @Override
    public void create(Column column) {
        dao.insert(translator.toDto(column));
    }

    @Override
    public void update(Column column) {
        dao.update(translator.toDto(column));
    }

    @Override
    public Optional<Column> columnFrom(ProjectId projectId, ColumnId columnId) {
        return dao.selectById(columnId.getValue(), projectId.getValue()).map(translator::toModel);
    }

}
