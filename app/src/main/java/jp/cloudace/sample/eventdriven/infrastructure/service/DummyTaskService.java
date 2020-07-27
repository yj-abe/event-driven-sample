package jp.cloudace.sample.eventdriven.infrastructure.service;

import java.util.UUID;

import jp.cloudace.sample.eventdriven.domain.model.google.tasks.Task;
import jp.cloudace.sample.eventdriven.domain.model.google.tasks.TaskService;

public class DummyTaskService implements TaskService {

    @Override
    public Task createTask(String title) {
        return new Task(UUID.randomUUID().toString(), title);
    }

}
