package ro.utcn.sd.agui.a1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class QuestionDTO {
    private Integer questionId;
    private Integer userId;
    private String title;
    private String text;
    private Timestamp dateTime;
    private List<String> tags;
    private Integer upVotes;
    private Integer downVotes;

    public QuestionDTO(Integer questionId, Integer userId, String title, String text, Timestamp dateTime, List<String> tags) {
        this.questionId = questionId;
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
        this.tags = tags;
    }

    public static QuestionDTO ofEntity(Question question, Integer upVotes, Integer downVotes) {
        QuestionDTO dto = new QuestionDTO(question.getQuestionId(), question.getUserId(),
                question.getTitle(), question.getText(), question.getDateTime(),
                question.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        dto.setUpVotes(upVotes);
        dto.setDownVotes(downVotes);
        return dto;
    }
}
