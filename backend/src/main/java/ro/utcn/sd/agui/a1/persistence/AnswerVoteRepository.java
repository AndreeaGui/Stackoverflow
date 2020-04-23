package ro.utcn.sd.agui.a1.persistence;

import ro.utcn.sd.agui.a1.entity.AnswerVote;
import ro.utcn.sd.agui.a1.entity.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerVoteRepository {

    AnswerVote save(AnswerVote answerVote);

    Optional<AnswerVote> findById(int id);

    void remove(AnswerVote answerVote);

    List<AnswerVote> findAll();

    List<AnswerVote> findAllPerAnswer(Answer answer);

}
