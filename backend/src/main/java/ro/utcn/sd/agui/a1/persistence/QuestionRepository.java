package ro.utcn.sd.agui.a1.persistence;

import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    Question save(Question question);

    Optional<Question> findById(int id);

    void remove(Question question);

    List<Question> findAll();

    List<Question> filterByTag(Tag tag);

}
