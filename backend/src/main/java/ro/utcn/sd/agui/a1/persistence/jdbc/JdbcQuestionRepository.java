package ro.utcn.sd.agui.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;
import ro.utcn.sd.agui.a1.persistence.QuestionRepository;
import ro.utcn.sd.agui.a1.persistence.jdbc.mapper.QuestionMapper;

import java.util.*;

@RequiredArgsConstructor
public class JdbcQuestionRepository implements QuestionRepository {

    private final JdbcTemplate template;

    @Override
    public Question save(Question question) {
        if (question.getQuestionId() != null) {
            update(question);
        } else {
            int id = insert(question);
            question.setQuestionId(id);
        }

        return question;
    }

    @Override
    public Optional<Question> findById(int id) {
        List<Question> questions = template.query("SELECT * FROM question WHERE question_id = ?",
                new Object[]{id},
                new QuestionMapper());
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void remove(Question question) {
        template.update("DELETE FROM question WHERE question_id = ?", question.getQuestionId());
    }

    @Override
    public List<Question> findAll() {
        List<Question> questions = new ArrayList<>();
        questions = template.query("SELECT * FROM question",
                new QuestionMapper());

        for (Question iterationQuestion : questions) {

            iterationQuestion.setTags(findAllTagsOfQuestion(iterationQuestion));
        }
        return questions;
    }

    private int insert(Question question) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question");
        insert.setGeneratedKeyName("question_id");

        Map<String, Object> data = new HashMap<>();
        data.put("user_id", question.getUserId());
        data.put("title", question.getTitle());
        data.put("text", question.getText());
        data.put("date_time", question.getDateTime());

        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Question question) {
        template.update("UPDATE question SET user_id = ?, title = ?, text = ?, date_time = ? WHERE question_id = ?",
                question.getUserId(), question.getTitle(), question.getText(), question.getDateTime(),
                question.getQuestionId());
    }

    private List<Tag> findAllTagsOfQuestion(Question question) {
        return template.query("SELECT * FROM tag WHERE tag_id IN (SELECT tag_id FROM question_tag WHERE question_id = ?)",
                new Object[]{question.getQuestionId()},
                (resultSet, i) -> new Tag(
                        resultSet.getInt("tag_id"),
                        resultSet.getString("name")
                ));
    }

    @Override
    public List<Question> filterByTag(Tag tag) {
        List<Question> questions = new ArrayList<>();
        questions = template.query("SELECT * FROM question JOIN question_tag ON question.question_id = question_tag.question_id WHERE tag_id = ?",
                new Object[]{tag.getTagId()},
                new QuestionMapper());

        for (Question iterationQuestion : questions) {

            iterationQuestion.setTags(findAllTagsOfQuestion(iterationQuestion));
        }

        return questions;
    }
}
