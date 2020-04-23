package ro.utcn.sd.agui.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ro.utcn.sd.agui.a1.persistence.AnswerVoteRepository;
import ro.utcn.sd.agui.a1.persistence.QuestionVoteRepository;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "JDBC")
public class JdbcRepositoryFactory implements RepositoryFactory {

    private final JdbcTemplate template;
    //passed as argument to each repository for the constructors
    //created by spring for us


    public JdbcUserRepository createUserRepository() {
        return new JdbcUserRepository(template);
    }

    public JdbcQuestionRepository createQuestionRepository() {

        return new JdbcQuestionRepository(template);
    }

    public JdbcAnswerRepository createAnswerRepository() {

        return new JdbcAnswerRepository(template);
    }

    public JdbcTagRepository createTagRepository() {

        return new JdbcTagRepository(template);
    }

    @Override
    public AnswerVoteRepository createAnswerVoteRepository() {
        return new JdbcAnswerVoteRepository(template);
    }

    @Override
    public QuestionVoteRepository createQuestionVoteRepository() {
        return new JdbcQuestionVoteRepository(template);
    }
}
