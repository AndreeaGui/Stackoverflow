package ro.utcn.sd.agui.a1.persistence.jdbc.mapper;

import ro.utcn.sd.agui.a1.entity.AnswerVote;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;;

public class AnswerVoteMapper implements RowMapper<AnswerVote> {

    @Override
    public AnswerVote mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new AnswerVote(
                resultSet.getInt("vote_id"),
                resultSet.getInt("answer_id"),
                resultSet.getInt("user_id"),
                resultSet.getBoolean("type")
        );
    }
}
