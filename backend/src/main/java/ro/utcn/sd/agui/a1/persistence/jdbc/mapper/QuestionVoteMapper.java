package ro.utcn.sd.agui.a1.persistence.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ro.utcn.sd.agui.a1.entity.QuestionVote;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionVoteMapper implements RowMapper<QuestionVote> {

    @Override
    public QuestionVote mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new QuestionVote(
                resultSet.getInt("vote_id"),
                resultSet.getInt("question_id"),
                resultSet.getInt("user_id"),
                resultSet.getBoolean("type")
        );
    }
}
