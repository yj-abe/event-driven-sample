package jp.cloudace.sample.eventdriven.application.usecase.column;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.application.persistence.Transactional;
import jp.cloudace.sample.eventdriven.domain.model.cloumn.Column;
import jp.cloudace.sample.eventdriven.domain.model.cloumn.ColumnService;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;

public class CreateColumnUseCase {

    private final ColumnService columnService;

    @Inject
    public CreateColumnUseCase(ColumnService columnService) {
        this.columnService = columnService;
    }

    @Transactional
    public String handle(String projectId, String name) {
        Column column = columnService.create(new ProjectId(projectId), name);
        return column.getId().getValue();
    }

}
