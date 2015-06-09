package example.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.mybatis.enums.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
// MyBatisがnew User()を使用するので必要
@NoArgsConstructor
// NoArgsConstructorとBuilderを定義するとBuilderのAllArgsConstructorが消えるので再定義
@AllArgsConstructor(access = AccessLevel.PACKAGE)
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

    private Integer version;
}
