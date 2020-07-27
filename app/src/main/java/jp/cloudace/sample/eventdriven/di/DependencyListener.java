package jp.cloudace.sample.eventdriven.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.annotation.WebListener;

import jp.cloudace.sample.eventdriven.application.di.ApplicationModule;
import jp.cloudace.sample.eventdriven.infrastructure.di.InfrastructureModule;
import jp.cloudace.sample.eventdriven.presentation.di.PresentationModule;

@WebListener
public class DependencyListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new InfrastructureModule(),
                new PresentationModule(),
                new ApplicationModule()
        );
    }

}
