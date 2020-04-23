package ro.utcn.sd.agui.a1.persistence.memory;

import ro.utcn.sd.agui.a1.entity.Answer;
import ro.utcn.sd.agui.a1.entity.AnswerVote;
import ro.utcn.sd.agui.a1.persistence.AnswerVoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAnswerVoteRepository implements AnswerVoteRepository {

    private final Map<Integer, AnswerVote> data = new ConcurrentHashMap<>();
    private final AtomicInteger current = new AtomicInteger(0);

    @Override
    public AnswerVote save(AnswerVote answerVote) {
        if (answerVote.getVoteId() == null) {
            answerVote.setVoteId(current.incrementAndGet());
        }
        data.put(answerVote.getVoteId(), answerVote);
        return answerVote;
    }

    @Override
    public Optional<AnswerVote> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(AnswerVote answerVote) {
        data.remove(answerVote.getVoteId());
    }

    @Override
    public List<AnswerVote> findAll() {
        return new ArrayList<AnswerVote>(data.values());
    }

    @Override
    public List<AnswerVote> findAllPerAnswer(Answer answer) {
        List<AnswerVote> votes = new ArrayList<AnswerVote>();
        for (AnswerVote voteIterator : data.values()) {
            if (voteIterator.getVoteId() == answer.getAnswerId()) {
                votes.add(voteIterator);
            }
        }
        return votes;
    }
}
