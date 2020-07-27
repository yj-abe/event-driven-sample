package jp.cloudace.sample.eventdriven.domain.model.note;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.domain.event.DomainEventPublisher;
import jp.cloudace.sample.eventdriven.domain.event.DomainResult;
import jp.cloudace.sample.eventdriven.domain.model.cloumn.Column;
import jp.cloudace.sample.eventdriven.domain.model.cloumn.ColumnId;
import jp.cloudace.sample.eventdriven.domain.model.cloumn.ColumnRepository;
import jp.cloudace.sample.eventdriven.domain.model.project.Project;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectRepository;

public class NoteService {

    private final ProjectRepository projectRepository;
    private final ColumnRepository columnRepository;
    private final NoteRepository noteRepository;
    private final DomainEventPublisher eventPublisher;

    @Inject
    public NoteService(
            ProjectRepository projectRepository,
            ColumnRepository columnRepository,
            NoteRepository noteRepository,
            DomainEventPublisher eventPublisher
    ) {
        this.projectRepository = projectRepository;
        this.columnRepository = columnRepository;
        this.noteRepository = noteRepository;
        this.eventPublisher = eventPublisher;
    }

    public Note createNote(ProjectId projectId, ColumnId columnId, String description) {

        Project project = projectRepository.projectOf(projectId)
                .orElseThrow(() -> new IllegalArgumentException("specified project is not found."));

        if (!project.isActive()) {
            throw new IllegalStateException("specified project is not active.");
        }

        Column column = columnRepository.columnFrom(projectId, columnId)
                .orElseThrow(() -> new IllegalArgumentException("specified column is not found."));

        if (!column.isActive()) {
            throw new IllegalStateException("specified column is not active.");
        }

        NoteId noteId = noteRepository.newId();
        DomainResult<Note, NoteEvent> domainResult = Note.create(
                noteId, projectId, columnId, description);

        Note note = domainResult.getResult();

        noteRepository.create(note);
        eventPublisher.publish(domainResult.getEvent());

        return note;
    }

}
