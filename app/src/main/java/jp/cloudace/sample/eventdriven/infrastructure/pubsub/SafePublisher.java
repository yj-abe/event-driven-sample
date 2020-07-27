package jp.cloudace.sample.eventdriven.infrastructure.pubsub;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.TopicName;

import jp.cloudace.sample.eventdriven.common.function.ThrowableConsumer;

public interface SafePublisher {

    void publish(TopicName topicName, ThrowableConsumer<Publisher> publisherConsumer);

}
