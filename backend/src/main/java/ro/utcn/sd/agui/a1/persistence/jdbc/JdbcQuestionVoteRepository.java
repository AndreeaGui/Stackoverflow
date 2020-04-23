package ro.utcn.sd.agui.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.QuestionVote;
import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.persistence.QuestionVoteRepository;
import ro.utcn.sd.agui.a1.persistence.jdbc.mapper.QuestionVoteMapper;

import java.util.*;

@RequiredArgsConstructor
public class JdbcQuestionVoteRepository implements QuestionVoteRepository {

    private final JdbcTemplate template;

    @Override
    public QuestionVote save(QuestionVote questionVote) {
        if (questionVote.getQuestionId() != null) {
            update(questionVote);
        } else {
            int id = insert(questionVote);
            questionVote.setQuestionId(id);
        }

        return questionVote;
    }

    @Override
    public Optional<QuestionVote> findById(int id) {
        List<QuestionVote> votes = template.query("SELECT * FROM question_vote WHERE vote_id = ?",
                new Object[]{id},
                new QuestionVoteMapper());
        return votes.isEmpty() ? Optional.empty() : Optional.of(votes.get(0));
    }

    @Override
    public void remove(QuestionVote questionVote) {
        template.update("DELETE FROM question_vote WHERE vote_id = ?", questionVote.getVoteId());
    }

    @Override
    public List<QuestionVote> findAll() {
        return template.query("SELECT * FROM question_vote", new QuestionVoteMapper());

    }

    @Override
    public Optional<QuestionVote> findByUserAndQuestion(User user, Question question){
        List<QuestionVote> votes = template.query("SELECT FROM question_vote WHERE (user_id = ? AND question_id =?)",
                new Object[]{user.getUserId(), question.getQuestionId()},
                new QuestionVoteMapper());
        return votes.isEmpty() ? Optional.empty() : Optional.of(votes.get(0));
    }

    private int insert(QuestionVote questionVote) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question_vote");
        insert.setGeneratedKeyName("vote_id");

        Map<String, Object> data = new HashMap<>();
        data.put("user_id", questionVote.getUserId());
        data.put("question_id", questionVote.getQuestionId());
        data.put("type", questionVote.getType());

        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(QuestionVote questionVote) {
        template.update("UPDATE question_vote SET question_id = ?, user_id = ?, type = ?, WHERE vote_id = ?",
                questionVote.getQuestionId(), questionVote.getUserId(), questionVote.getType(),
                questionVote.getVoteId());
    }
}
