package jp.cloudace.sample.eventdriven.application.di;

import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.ServletModule;

import jp.cloudace.sample.eventdriven.application.event.EnqueueEvent;
import jp.cloudace.sample.eventdriven.application.event.EnqueueEventInterceptor;

public class ApplicationModule extends ServletModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();

        EnqueueEventInterceptor enqueueEventInterceptor = new EnqueueEventInterceptor();
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(EnqueueEvent.class),
                enqueueEventInterceptor);
        requestInjection(enqueueEventInterceptor);

    }


}
