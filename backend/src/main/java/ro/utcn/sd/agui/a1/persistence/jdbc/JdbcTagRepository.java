package ro.utcn.sd.agui.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;
import ro.utcn.sd.agui.a1.persistence.TagRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcTagRepository implements TagRepository {

    private final JdbcTemplate template;

    @Override
    public Tag save(Tag tag) {
        if (tag.getTagId() == null) {
            tag.setTagId(insert(tag));
        } else {
            update(tag);
        }
        return tag;
    }

    @Override
    public Optional<Tag> findById(int id) {
        List<Tag> tags = template.query("SELECT * FROM tag WHERE tag_id = ?",
                new Object[]{id},
                ((resultSet, i) -> new Tag(
                        resultSet.getInt("tag_id"),
                        resultSet.getString("name")
                )));

        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public void remove(Tag tag) {
        template.update("DELETE FROM tag WHERE tag_id = ?", tag.getTagId());
    }

    @Override
    public List<Tag> findAll() {
        return template.query("SELECT * FROM tag",
                (resultSet, i) -> new Tag(
                        resultSet.getInt("tag_id"),
                        resultSet.getString("name")
                ));
    }

    private Integer insert(Tag tag) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("tag");
        insert.usingGeneratedKeyColumns("tag_id");

        Map<String, Object> data = new HashMap<>();

        data.put("name", tag.getName());

        return insert.executeAndReturnKey(data).intValue();

    }

    private void update(Tag tag) {
        template.update("UPDATE tag SET tag_name = ? WHERE tag_id = ? ", tag.getName(), tag.getTagId());
    }

    @Override
    public void addTagToQuestion(Tag tag, Question question) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question_tag");
        insert.usingGeneratedKeyColumns("question_tag_id");

        Map<String, Object> data = new HashMap<>();
        data.put("question_id", question.getQuestionId());
        data.put("tag_id", tag.getTagId());
        insert.executeAndReturnKey(data).intValue();

    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> tags = template.query("SELECT * FROM tag WHERE name = ?",
                new Object[]{name},
                ((resultSet, i) -> new Tag(
                        resultSet.getInt("tag_id"),
                        resultSet.getString("name")
                )));

        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }
}
