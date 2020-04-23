package ro.utcn.sd.agui.a1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuestionTag {
    private Integer questionTagId;
    private Integer questionId;
    private Integer tagId;
}
