package ro.utcn.sd.agui.a1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Answer {

    private Integer answerId;
    private Integer questionId;
    private Integer userId;
    private String text;
    private Timestamp dateTime;

}
