package ro.utcn.sd.agui.a1.persistence.memory;

import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.QuestionVote;
import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.persistence.QuestionVoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryQuestionVoteRepository implements QuestionVoteRepository {

    private final Map<Integer, QuestionVote> data = new ConcurrentHashMap<>();
    private final AtomicInteger current = new AtomicInteger(0);

    @Override
    public QuestionVote save(QuestionVote questionVote) {
        if (questionVote.getVoteId() == null) {
            questionVote.setVoteId(current.incrementAndGet());
        }
        data.put(questionVote.getVoteId(), questionVote);
        return questionVote;
    }

    @Override
    public Optional<QuestionVote> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(QuestionVote questionVote) {
        data.remove(questionVote.getVoteId());
    }

    @Override
    public List<QuestionVote> findAll() {
        return new ArrayList<QuestionVote>(data.values());
    }

    @Override
    public Optional<QuestionVote> findByUserAndQuestion(User user, Question question) {
        List<QuestionVote> votes = new ArrayList<>();
        for(QuestionVote voteIterator: data.values()){
            if(voteIterator.getUserId() == user.getUserId() && voteIterator.getQuestionId() == question.getQuestionId()){
                return Optional.ofNullable(voteIterator);
            }
        }
        return Optional.empty();
    }
}
