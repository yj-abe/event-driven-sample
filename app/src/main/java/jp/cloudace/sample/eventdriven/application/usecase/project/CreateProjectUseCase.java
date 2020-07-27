package jp.cloudace.sample.eventdriven.application.usecase.project;

import javax.inject.Inject;

import jp.cloudace.sample.eventdriven.application.persistence.Transactional;
import jp.cloudace.sample.eventdriven.domain.model.project.Project;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectId;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectRepository;

public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;

    @Inject
    public CreateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public String handle(String name) {
        ProjectId id = projectRepository.newId();
        Project project = Project.create(id, name);
        projectRepository.create(project);
        return project.getId().getValue();
    }

}
