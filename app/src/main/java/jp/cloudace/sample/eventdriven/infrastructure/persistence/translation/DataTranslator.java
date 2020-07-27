package jp.cloudace.sample.eventdriven.infrastructure.persistence.translation;

public interface DataTranslator<Model, Dto> {

    Model toModel(Dto dto);

    Dto toDto(Model model);

}
