package example.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.mybatis.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;

    private String loginId;

    @JsonIgnore // パスワードなのでJSONでは非表示
    private String encodedPassword;

    private String name;

    private String mailAddress;

    private UserType userType;

    private LocalDateTime created;

    private LocalDateTime updated;

    private int version;
}
