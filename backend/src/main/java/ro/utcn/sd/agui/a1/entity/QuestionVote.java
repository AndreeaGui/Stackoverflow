package ro.utcn.sd.agui.a1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionVote {

    private Integer voteId;
    private Integer questionId;
    private Integer userId;
    private Boolean type;
}
