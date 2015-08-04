package example.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import example.mybatis.enums.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
// MyBatisがnew User()を使用するので必要
@NoArgsConstructor
// NoArgsConstructorとBuilderを定義するとBuilderのAllArgsConstructorが消えるので再定義
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    private Integer id;

    private String loginId;

    @JsonIgnore // パスワードなのでJSONでは非表示
    private String encodedPassword;

    private String name;

    private String mailAddress;

    private UserType userType;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss.SSS")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss.SSS")
    private LocalDateTime updated;

    private Integer version;
}
