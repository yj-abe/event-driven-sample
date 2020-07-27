package jp.cloudace.sample.eventdriven.application.usecase.note;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateNoteCommand {

    String projectId;
    String columnId;
    String description;

}
