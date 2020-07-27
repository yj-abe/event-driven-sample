package jp.cloudace.sample.eventdriven.application.usecase.note;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.application.persistence.Transactional;
import jp.cloudace.sample.eventdriven.domain.model.google.tasks.Task;
import jp.cloudace.sample.eventdriven.domain.model.google.tasks.TaskService;
import jp.cloudace.sample.eventdriven.domain.model.note.NoteId;
import jp.cloudace.sample.eventdriven.domain.model.note.NoteRepository;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;

public class CopyNoteToTaskUseCase {

    private final NoteRepository noteRepository;
    private final TaskService taskService;

    @Inject
    public CopyNoteToTaskUseCase(NoteRepository noteRepository, TaskService taskService) {
        this.noteRepository = noteRepository;
        this.taskService = taskService;
    }

    @Transactional
    public void handle(String projectId, String noteId) {
        ProjectId pId = new ProjectId(projectId);
        NoteId nId = new NoteId(noteId);
        noteRepository.noteFrom(pId, nId).ifPresent(note -> {
            Task task = taskService.createTask(note.getDescription());
            System.out.println("Task created : " + task.getId());
        });
    }

}
