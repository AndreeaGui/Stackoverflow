package ro.utcn.sd.agui.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.agui.a1.entity.Answer;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.persistence.AnswerRepository;
import ro.utcn.sd.agui.a1.persistence.jdbc.mapper.AnswerMapper;

import java.util.*;

@RequiredArgsConstructor
public class JdbcAnswerRepository implements AnswerRepository {

    private final JdbcTemplate template;

    @Override
    public Answer save(Answer answer) {
        if (answer.getAnswerId() != null) {
            update(answer);
        } else {
            int id = insert(answer);
            answer.setAnswerId(id);
        }

        return answer;
    }

    @Override
    public Optional<Answer> findById(int id) {
        List<Answer> answer = template.query("SELECT * FROM answer WHERE answer_id = ?",
                new Object[]{id},
                new AnswerMapper());
        return answer.isEmpty() ? Optional.empty() : Optional.of(answer.get(0));
    }

    @Override
    public void remove(Answer answer) {
        template.update("DELETE FROM answer WHERE answer_id = ?", new Object[]{answer.getAnswerId()});
    }

    @Override
    public List<Answer> findAll() {
        List<Answer> answers = new ArrayList<>();
        answers = template.query("SELECT * FROM answer",
                new AnswerMapper());

        return answers;
    }

    @Override
    public List<Answer> findAllByQuestion(Question question) {
        List<Answer> answers = template.query("SELECT * FROM answer WHERE question_id = ?",
                new Object[]{question.getQuestionId()},
                new AnswerMapper());
        return answers;
    }

    private void update(Answer answer) {
        template.update("UPDATE answer SET question_id = ?, user_id = ?, text = ?, date_time = ? WHERE answer_id = ?",
                answer.getQuestionId(), answer.getUserId(), answer.getText(), answer.getDateTime(), answer.getAnswerId());

    }

    private int insert(Answer answer) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("answer");
        insert.setGeneratedKeyName("answer_id");

        Map<String, Object> data = new HashMap<>();
        data.put("question_id", answer.getQuestionId());
        data.put("user_id", answer.getUserId());
        data.put("text", answer.getText());
        data.put("date_time", answer.getDateTime());

        return insert.executeAndReturnKey(data).intValue();
    }
}
