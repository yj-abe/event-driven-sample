package jp.cloudace.sample.eventdriven.common.function;

public interface ThrowableConsumer<T> {
    void accept(T t) throws Exception;
}
