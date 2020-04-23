package ro.utcn.sd.agui.a1.persistence.jdbc.mapper;

import ro.utcn.sd.agui.a1.entity.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Question(resultSet.getInt("question_id"),
                resultSet.getInt("user_id"),
                resultSet.getString("title"),
                resultSet.getString("text"),
                resultSet.getTimestamp("date_time"));
    }

}
