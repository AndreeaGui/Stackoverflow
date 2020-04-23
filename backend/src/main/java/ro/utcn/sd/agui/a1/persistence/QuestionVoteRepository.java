package ro.utcn.sd.agui.a1.persistence;

import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.QuestionVote;
import ro.utcn.sd.agui.a1.entity.User;

import java.util.List;
import java.util.Optional;

public interface QuestionVoteRepository {

    QuestionVote save(QuestionVote questionVote);

    Optional<QuestionVote> findById(int id);

    void remove(QuestionVote questionVote);

    List<QuestionVote> findAll();

    Optional<QuestionVote> findByUserAndQuestion(User user, Question question);


}
