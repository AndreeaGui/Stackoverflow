package ro.utcn.sd.agui.a1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnswerVote {

    private Integer voteId;
    private Integer answerId;
    private Integer userId;
    private Boolean type;

}
