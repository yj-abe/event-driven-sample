package jp.cloudace.sample.eventdriven.domain.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ConcurrencyEntity extends Entity {

    private long version;

}
