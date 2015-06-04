package example.web.form;

import example.mybatis.domain.User;
import example.mybatis.enums.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserForm implements Serializable {
    public interface Insert {
    }

    public interface Update {
    }

    @NotEmpty(groups = Insert.class, message = "loginIdは必須です")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", groups = {Insert.class, Update.class}, message = "loginIdは英数字です")
    @Length(max = 100, groups = {Insert.class}, message = "loginIdは{max}以下です")
    private String loginId;

    @NotNull(groups = Insert.class, message = "passwordは必須です")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", groups = {Insert.class, Update.class}, message = "passwordは英数字です")
    @Length(min = 5, max = 100, groups = {Insert.class, Update.class}, message = "passwordは{min}以上{max}以下です")
    private String password;

    @NotEmpty(groups = Insert.class, message = "nameは必須です")
    @Length(max = 100, groups = {Insert.class, Update.class}, message = "nameは{max}以下です")
    private String name;

    @NotNull(groups = Insert.class, message = "mailは必須です")
    @Length(max = 100, groups = {Insert.class, Update.class}, message = "mailは{max}以下です")
    @Email(groups = {Insert.class, Update.class}, message = "mailのフォーマットが不正です")
    private String mail;

    @NotNull(groups = Insert.class, message = "userTypeは必須です")
    @Pattern(regexp = "^(8|12)$", groups = {Insert.class, Update.class}, message = "userTypeは8か12です")
    private String userType;

    public User getUser() {
        return User.builder().loginId(loginId)
                .encodedPassword(password)
                .name(name).mailAddress(mail)
                .userType(UserType.fromCode(userType))
                .build();
    }
}
