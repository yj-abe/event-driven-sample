package jp.cloudace.sample.eventdriven.infrastructure.persistence.translation;

import jp.cloudace.sample.eventdriven.domain.model.cloumn.ColumnId;
import jp.cloudace.sample.eventdriven.domain.model.note.Note;
import jp.cloudace.sample.eventdriven.domain.model.note.NoteId;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.entity.NoteDto;

public class NoteTranslator extends ConcurrencyDataTranslator<Note, NoteDto> {

    @Override
    public Note toModel(NoteDto dto) {
        Note note = new Note(
                new NoteId(dto.getId()),
                new ProjectId(dto.getProjectId()),
                new ColumnId(dto.getColumnId()),
                dto.getDescription()
        );
        attachToModel(dto, note);
        return note;
    }

    @Override
    public NoteDto toDto(Note note) {
        NoteDto dto = new NoteDto();
        dto.setId(note.getId().getValue());
        dto.setProjectId(note.getProjectId().getValue());
        dto.setColumnId(note.getColumnId().getValue());
        dto.setDescription(note.getDescription());
        attachToDto(note, dto);
        return dto;
    }

}
