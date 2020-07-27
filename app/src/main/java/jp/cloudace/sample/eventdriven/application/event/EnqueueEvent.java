package jp.cloudace.sample.eventdriven.application.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.cloudace.sample.eventdriven.domain.event.DomainEvent;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EnqueueEvent {

    Class<? extends DomainEvent>[] value();

}
