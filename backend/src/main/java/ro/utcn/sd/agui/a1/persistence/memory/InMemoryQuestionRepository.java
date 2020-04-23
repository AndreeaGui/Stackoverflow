package ro.utcn.sd.agui.a1.persistence.memory;


import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;
import ro.utcn.sd.agui.a1.persistence.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryQuestionRepository implements QuestionRepository {

    private final Map<Integer, Question> data = new ConcurrentHashMap<>();
    private final AtomicInteger current = new AtomicInteger(0);


    @Override
    public Question save(Question question) {

        if (question.getQuestionId() == null) {
            question.setQuestionId(current.incrementAndGet());
        }
        data.put(question.getQuestionId(), question);
        return question;
    }

    @Override
    public Optional<Question> findById(int id) {

        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(Question question) {
        data.remove(question.getQuestionId());
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<Question>(data.values());
    }

    @Override
    public List<Question> filterByTag(Tag tag) {
        List<Question> filteredQuestions = new ArrayList<>();
        List<Question> questions = new ArrayList<>(data.values());
        for (Question questionIterator : questions) {
            List<Tag> tags = questionIterator.getTags();

            if (tags.stream().anyMatch(x -> x.getName().equals(tag.getName()))) {
                filteredQuestions.add(questionIterator);
            }

        }


        return filteredQuestions;
    }
}
