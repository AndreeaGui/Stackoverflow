package ro.utcn.sd.agui.a1.persistence;



public interface RepositoryFactory {

    UserRepository createUserRepository();

    QuestionRepository createQuestionRepository();

    AnswerRepository createAnswerRepository();

    TagRepository createTagRepository();

    AnswerVoteRepository createAnswerVoteRepository();

    QuestionVoteRepository createQuestionVoteRepository();

}
