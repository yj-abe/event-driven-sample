package jp.cloudace.sample.eventdriven.presentation.di;

import com.google.inject.servlet.ServletModule;

import jp.cloudace.sample.eventdriven.presentation.api.ColumnApi;
import jp.cloudace.sample.eventdriven.presentation.api.NoteApi;
import jp.cloudace.sample.eventdriven.presentation.api.ProjectApi;
import jp.cloudace.sample.eventdriven.presentation.event.EventProxy;
import jp.cloudace.sample.eventdriven.presentation.event.receiver.NoteCreatedReceiver;

public class PresentationModule extends ServletModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();

        // api
        serve("/api/projects").with(ProjectApi.class);
        serve("/api/columns").with(ColumnApi.class);
        serve("/api/notes").with(NoteApi.class);

        // event receiver
        serve("/event/proxy").with(EventProxy.class);
        serve("/event/receiver/note-created").with(NoteCreatedReceiver.class);

    }
}
