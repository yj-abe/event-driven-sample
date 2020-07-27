package jp.cloudace.sample.eventdriven.presentation.event;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.annotation.WebServlet;

import jp.cloudace.sample.eventdriven.application.event.EventRelay;

@Singleton
@WebServlet(value = "/event/proxy")
public class EventProxy extends EventReceiver {

    @Inject
    private EventRelay eventRelay;

    @Override
    protected void onReceive(Event event) {
        eventRelay.relay(event.getEventId());
    }

}
