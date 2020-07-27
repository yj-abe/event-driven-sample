package jp.cloudace.sample.eventdriven.application.usecase.project;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.application.event.EventQueue;
import jp.cloudace.sample.eventdriven.application.persistence.Transactional;
import jp.cloudace.sample.eventdriven.domain.model.project.Project;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectRepository;

public class DeactivateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final EventQueue eventQueue;

    @Inject
    public DeactivateProjectUseCase(
            ProjectRepository projectRepository,
            EventQueue eventQueue
    ) {
        this.projectRepository = projectRepository;
        this.eventQueue = eventQueue;
    }

    @Transactional
    public void handle(String projectId) {
        projectRepository.projectOf(new ProjectId(projectId))
                .flatMap(Project::deactivate)
                .ifPresent(eventQueue::push);
    }

}
