package jp.cloudace.sample.eventdriven.infrastructure.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.ServletModule;

import java.util.Date;

import jp.cloudace.sample.eventdriven.application.event.EventDispatcher;
import jp.cloudace.sample.eventdriven.application.event.EventStore;
import jp.cloudace.sample.eventdriven.application.persistence.Transactional;
import jp.cloudace.sample.eventdriven.domain.model.cloumn.ColumnRepository;
import jp.cloudace.sample.eventdriven.domain.model.google.tasks.TaskService;
import jp.cloudace.sample.eventdriven.domain.model.note.NoteRepository;
import jp.cloudace.sample.eventdriven.domain.model.project.ProjectRepository;
import jp.cloudace.sample.eventdriven.infrastructure.event.DomainEventResetFilter;
import jp.cloudace.sample.eventdriven.infrastructure.event.MySqlConsumedEventStore;
import jp.cloudace.sample.eventdriven.infrastructure.event.MySqlEventStore;
import jp.cloudace.sample.eventdriven.infrastructure.event.PubsubEventDispatcher;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.ColumnDao;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.ColumnDaoImpl;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.ConsumedEventDao;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.ConsumedEventDaoImpl;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.EventDao;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.EventDaoImpl;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.NoteDao;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.NoteDaoImpl;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.ProjectDao;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.dao.ProjectDaoImpl;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.doma.DomaConfig;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.doma.DomaTransactionInterceptor;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.repository.MySqlColumnRepository;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.repository.MySqlNoteRepository;
import jp.cloudace.sample.eventdriven.infrastructure.persistence.repository.MySqlProjectRepository;
import jp.cloudace.sample.eventdriven.infrastructure.pubsub.LocalSafePublisher;
import jp.cloudace.sample.eventdriven.infrastructure.pubsub.RemoteSafePublisher;
import jp.cloudace.sample.eventdriven.infrastructure.pubsub.SafePublisher;
import jp.cloudace.sample.eventdriven.infrastructure.serialization.DateTypeAdapter;
import jp.cloudace.sample.eventdriven.infrastructure.service.DummyTaskService;
import jp.cloudace.sample.eventdriven.presentation.event.ConsumedEventStore;

public class InfrastructureModule extends ServletModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        bind(Gson.class).toInstance(gson);

        // pubsub
        String port = System.getenv("PUBSUB_EMULATOR_HOST");
        if (port != null) {
            System.out.println("emulator port : " + port);
            bind(SafePublisher.class).toInstance(new LocalSafePublisher(port));
        } else {
            bind(SafePublisher.class).to(RemoteSafePublisher.class);
        }

        // dao
        bind(ProjectDao.class).toInstance(new ProjectDaoImpl(DomaConfig.getInstance()));
        bind(ColumnDao.class).toInstance(new ColumnDaoImpl(DomaConfig.getInstance()));
        bind(NoteDao.class).toInstance(new NoteDaoImpl(DomaConfig.getInstance()));
        bind(EventDao.class).toInstance(new EventDaoImpl(DomaConfig.getInstance()));
        bind(ConsumedEventDao.class).toInstance(new ConsumedEventDaoImpl(DomaConfig.getInstance()));

        // repository
        bind(ProjectRepository.class).to(MySqlProjectRepository.class);
        bind(ColumnRepository.class).to(MySqlColumnRepository.class);
        bind(NoteRepository.class).to(MySqlNoteRepository.class);

        // interceptor
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class),
                new DomaTransactionInterceptor(DomaConfig.getInstance()));

        // event
        bind(EventStore.class).to(MySqlEventStore.class);
        bind(EventDispatcher.class).to(PubsubEventDispatcher.class);
        bind(ConsumedEventStore.class).to(MySqlConsumedEventStore.class);

        // service
        bind(TaskService.class).to(DummyTaskService.class);


        // filter
        filter("/*").through(DomainEventResetFilter.class);

    }
}
