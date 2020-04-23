package ro.utcn.sd.agui.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.agui.a1.entity.Answer;
import ro.utcn.sd.agui.a1.entity.AnswerVote;
import ro.utcn.sd.agui.a1.persistence.AnswerVoteRepository;
import ro.utcn.sd.agui.a1.persistence.jdbc.mapper.AnswerVoteMapper;

import java.util.*;

@RequiredArgsConstructor
public class JdbcAnswerVoteRepository implements AnswerVoteRepository {

    private final JdbcTemplate template;

    @Override
    public AnswerVote save(AnswerVote answerVote) {
        if (answerVote.getVoteId() != null) {
            update(answerVote);
        } else {
            int id = insert(answerVote);
            answerVote.setVoteId(id);
        }

        return answerVote;
    }

    @Override
    public Optional<AnswerVote> findById(int id) {
        List<AnswerVote> answerVote = template.query("SELECT * FROM answer_vote WHERE vote_id = ?",
                new Object[]{id},
                new AnswerVoteMapper());
        return answerVote.isEmpty() ? Optional.empty() : Optional.of(answerVote.get(0));
    }

    @Override
    public void remove(AnswerVote answerVote) {
        template.update("DELETE FROM answer_vote WHERE vote_id = ?",
                new Object[]{answerVote.getVoteId()});
    }

    @Override
    public List<AnswerVote> findAll() {

        return template.query("SELECT * FROM answer_vote", new AnswerVoteMapper());

    }

    @Override
    public List<AnswerVote> findAllPerAnswer(Answer answer) {
        List<AnswerVote> votes = template.query("SELECT * FROM answer_vote WHERE answer_id = ?",
                new Object[]{answer.getAnswerId()},
                new AnswerVoteMapper());
        return votes;
    }

    private void update(AnswerVote answerVote) {
        template.update("UPDATE answer_vote SET answer_id = ?, user_id = ?, type = ? WHERE vote_id = ?",
                answerVote.getAnswerId(), answerVote.getUserId(), answerVote.getType(), answerVote.getVoteId());

    }

    private int insert(AnswerVote answerVote) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("answer_vote");
        insert.setGeneratedKeyName("vote_id");

        Map<String, Object> data = new HashMap<>();
        data.put("answer_id", answerVote.getAnswerId());
        data.put("user_id", answerVote.getUserId());
        data.put("type", answerVote.getType());

        return insert.executeAndReturnKey(data).intValue();
    }
}
