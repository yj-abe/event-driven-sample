package jp.cloudace.sample.eventdriven.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.domain.model.note.Note;
import jp.cloudace.sample.eventdriven.domain.model.note.NoteId;
import jp.cloudace.sample.eventdriven.domain.model.note.NoteRepository;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.NoteDao;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.translation.NoteTranslator;

public class MySqlNoteRepository implements NoteRepository {

    private final NoteDao dao;
    private final NoteTranslator translator;

    @Inject
    public MySqlNoteRepository(NoteDao dao, NoteTranslator translator) {
        this.dao = dao;
        this.translator = translator;
    }

    @Override
    public NoteId newId() {
        return new NoteId(UUID.randomUUID().toString());
    }

    @Override
    public void create(Note note) {
        dao.insert(translator.toDto(note));
    }

    @Override
    public void update(Note note) {
        dao.update(translator.toDto(note));
    }

    @Override
    public Optional<Note> noteFrom(ProjectId projectId, NoteId noteId) {
        return dao.selectById(noteId.getValue(), projectId.getValue()).map(translator::toModel);
    }

}
