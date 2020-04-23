package ro.utcn.sd.agui.a1.persistence;

import ro.utcn.sd.agui.a1.entity.Answer;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository {

    Answer save(Answer answer);

    Optional<Answer> findById(int id);

    void remove(Answer answer);

    List<Answer> findAll();

    List<Answer> findAllByQuestion(Question question);


}
