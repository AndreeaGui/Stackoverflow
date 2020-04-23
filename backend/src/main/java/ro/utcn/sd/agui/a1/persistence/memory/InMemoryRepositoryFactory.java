package ro.utcn.sd.agui.a1.persistence.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;

@Component
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {

    private final InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
    private final InMemoryAnswerRepository answerRepository = new InMemoryAnswerRepository();
    private final InMemoryTagRepository tagRepository = new InMemoryTagRepository();
    private final InMemoryAnswerVoteRepository answerVoteRepository = new InMemoryAnswerVoteRepository();
    private final InMemoryQuestionVoteRepository questionVoteRepository = new InMemoryQuestionVoteRepository();

    public InMemoryUserRepository createUserRepository() {
        return userRepository;
    }

    public InMemoryQuestionRepository createQuestionRepository() {
        return questionRepository;
    }

    public InMemoryAnswerRepository createAnswerRepository() {
        return answerRepository;
    }

    public InMemoryTagRepository createTagRepository() {
        return tagRepository;
    }

    public InMemoryAnswerVoteRepository createAnswerVoteRepository() {
        return answerVoteRepository;
    }

    public InMemoryQuestionVoteRepository createQuestionVoteRepository() {
        return questionVoteRepository;
    }


}
