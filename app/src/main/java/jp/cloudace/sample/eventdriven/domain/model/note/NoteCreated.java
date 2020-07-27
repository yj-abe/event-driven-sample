package jp.cloudace.sample.eventdriven.domain.model.note;

import jp.cloudace.sample.eventdriven.domain.model.cloumn.ColumnId;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;
import lombok.Value;

@Value
public class NoteCreated implements NoteEvent {

    ProjectId projectId;
    ColumnId columnId;
    NoteId noteId;

}
