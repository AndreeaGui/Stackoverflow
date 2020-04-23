package ro.utcn.sd.agui.a1.persistence.memory;

import ro.utcn.sd.agui.a1.entity.Answer;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.persistence.AnswerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAnswerRepository implements AnswerRepository {

    private final Map<Integer, Answer> data = new ConcurrentHashMap<>();
    private final AtomicInteger current = new AtomicInteger(0);

    @Override
    public Answer save(Answer answer) {
        if (answer.getAnswerId() == null) {
            answer.setAnswerId(current.incrementAndGet());
        }
        data.put(answer.getAnswerId(), answer);
        return answer;
    }

    @Override
    public Optional<Answer> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(Answer answer) {
        data.remove(answer.getAnswerId());
    }

    @Override
    public List<Answer> findAll() {
        return new ArrayList<Answer>(data.values());
    }

    @Override
    public List<Answer> findAllByQuestion(Question question) {
        List<Answer> answers = new ArrayList<Answer>();
        for (Answer answerIterator : data.values()) {
            if (answerIterator.getQuestionId() == question.getQuestionId()) {
                answers.add(answerIterator);
            }
        }
        return answers;
    }


}
