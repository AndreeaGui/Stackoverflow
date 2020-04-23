package ro.utcn.sd.agui.a1.persistence;

import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);

    Optional<Tag> findById(int id);

    void remove(Tag tag);

    List<Tag> findAll();

    void addTagToQuestion(Tag tag, Question question);

    Optional<Tag> findByName(String name);
}
