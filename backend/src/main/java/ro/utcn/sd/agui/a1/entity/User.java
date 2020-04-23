package ro.utcn.sd.agui.a1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class User {

    private Integer userId;
    private String username;
    private String password;
    private Integer score;
    private String type;
    private Boolean banned;
}
