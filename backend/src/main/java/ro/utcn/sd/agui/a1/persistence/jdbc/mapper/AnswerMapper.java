package ro.utcn.sd.agui.a1.persistence.jdbc.mapper;

import ro.utcn.sd.agui.a1.entity.Answer;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerMapper implements RowMapper<Answer> {

    @Override
    public Answer mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Answer(
                resultSet.getInt("answer_id"),
                resultSet.getInt("question_id"),
                resultSet.getInt("user_id"),
                resultSet.getString("text"),
                resultSet.getTimestamp("date_time")
        );
    }
}
