package ro.utcn.sd.agui.a1.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.utcn.sd.agui.a1.entity.*;
import ro.utcn.sd.agui.a1.persistence.*;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)

public class UserSeed implements CommandLineRunner {

    private final RepositoryFactory factory;
    private  final PasswordEncoder passwordEncoder;

    public void run(String... args) throws Exception {
        UserRepository userRepository = factory.createUserRepository();
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(new User(null, "A", passwordEncoder.encode("abc1"), 0, "USER", false));
            userRepository.save(new User(null, "B", passwordEncoder.encode("abc2"), 0, "USER", false));
            userRepository.save(new User(null, "C", passwordEncoder.encode("abc3"), 0, "USER", false));
            userRepository.save(new User(null, "ana", passwordEncoder.encode("000"), 0, "USER", false));
        }

        QuestionRepository questionRepository = factory.createQuestionRepository();
        Question question1 = new Question(null, 1, "Title One", "Text One", new Timestamp(System.currentTimeMillis()));
        Question question2 = new Question(null, 2, "Title Two", "Text Two", new Timestamp(System.currentTimeMillis()));
        Question question3 = new Question(null, 2, "Title Three", "Text three", new Timestamp(System.currentTimeMillis()));
        if (questionRepository.findAll().isEmpty()) {
            questionRepository.save(question1);
            questionRepository.save(question2);
            questionRepository.save(question3);

        }

        AnswerRepository answerRepository = factory.createAnswerRepository();
        if (answerRepository.findAll().isEmpty()) {
            answerRepository.save(new Answer(null, 1, 4,
                    "Answer 1 for question 1", new Timestamp(System.currentTimeMillis())));
            answerRepository.save(new Answer(null, 1, 2,
                    "Answer 2 for question 1", new Timestamp(System.currentTimeMillis())));
            answerRepository.save(new Answer(null, 2, 1,
                    "Answer 1 for question 2", new Timestamp(System.currentTimeMillis())));
            answerRepository.save(new Answer(null, 3, 4,
                    "Answer 1 for question 3", new Timestamp(System.currentTimeMillis())));
        }

        TagRepository tagRepository = factory.createTagRepository();
        Tag tag1 = new Tag(null, "tag1");
        Tag tag2 = new Tag(null, "tag2");
        Tag tag3 = new Tag(null, "tag3");
        if(tagRepository.findAll().isEmpty()){
            tagRepository.save(tag1);
            tagRepository.save(tag2);
            tagRepository.save(tag3);
            tagRepository.addTagToQuestion(tag1,question1);
            tagRepository.addTagToQuestion(tag1, question2);
            tagRepository.addTagToQuestion(tag2, question2);
        }

        AnswerVoteRepository answerVoteRepository = factory.createAnswerVoteRepository();
        AnswerVote answerVote1 = new AnswerVote(null, 1, 3, false);
        AnswerVote answerVote2 = new AnswerVote(null, 1, 2, false);
        AnswerVote answerVote3 = new AnswerVote(null, 2, 1, true);
        if(answerVoteRepository.findAll().isEmpty()){
            answerVoteRepository.save(answerVote1);
            answerVoteRepository.save(answerVote2);
            answerVoteRepository.save(answerVote3);
        }

    }
}
