package jp.cloudace.sample.eventdriven.domain.model.project;

import lombok.Value;

@Value
public class ProjectDeactivated implements ProjectEvent {

    ProjectId projectId;

}
