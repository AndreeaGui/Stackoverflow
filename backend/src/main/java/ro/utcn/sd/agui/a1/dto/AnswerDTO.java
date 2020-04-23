package ro.utcn.sd.agui.a1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.sd.agui.a1.entity.Answer;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;

import java.sql.Timestamp;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AnswerDTO {
    private Integer answerId;
    private Integer userId;
    private Integer questionId;
    private String text;
    private Timestamp dateTime;
    private Integer upVotes;
    private Integer downVotes;

    public AnswerDTO(Integer answerId, Integer userId, Integer questionId, String text, Timestamp dateTime){
        this.answerId = answerId;
        this.questionId = questionId;
        this.userId = userId;
        this.text = text;
        this.dateTime = dateTime;
    }

    public static AnswerDTO ofEntity(Answer answer, Integer upVotes, Integer downVotes) {
        AnswerDTO dto = new AnswerDTO(answer.getAnswerId(), answer.getUserId(), answer.getQuestionId(),
                answer.getText(), answer.getDateTime());
        dto.setUpVotes(upVotes);
        dto.setDownVotes(downVotes);
        return dto;
    }
}
