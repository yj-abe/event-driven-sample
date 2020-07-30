package jp.cloudace.sample.eventdriven.application.usecase.note;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.application.persistence.Transactional;
import jp.cloudace.sample.eventdriven.domain.model.google.tasks.TaskCreationService;
import jp.cloudace.sample.eventdriven.domain.model.note.NoteId;
import jp.cloudace.sample.eventdriven.domain.model.note.NoteRepository;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;

public class CopyNoteToTaskUseCase {

    private final NoteRepository noteRepository;
    private final TaskCreationService taskCreationService;

    @Inject
    public CopyNoteToTaskUseCase(NoteRepository noteRepository, TaskCreationService taskCreationService) {
        this.noteRepository = noteRepository;
        this.taskCreationService = taskCreationService;
    }

    @Transactional
    public void handle(String projectId, String noteId) {
        ProjectId pId = new ProjectId(projectId);
        NoteId nId = new NoteId(noteId);
        noteRepository.noteFrom(pId, nId)
                .map(note -> note.copyToTasks(taskCreationService))
                .ifPresent(task -> System.out.println("Task created : " + task.getId()));
    }

}
