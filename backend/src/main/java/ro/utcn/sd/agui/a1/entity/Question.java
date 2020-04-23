package ro.utcn.sd.agui.a1.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {

    private Integer questionId;
    private Integer userId;
    private String title;
    private String text;
    private Timestamp dateTime;

    //many to may tag relationship
    private List<Tag> tags;

    //one to many answer relationship
    //private List<Answer> answers;

    public Question(Integer questionId, Integer userId, String title, String text, Timestamp dateTime) {
        this.questionId = questionId;
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
        this.tags = new ArrayList<>();
        //this.tags = new ArrayList<>();
    }
}
