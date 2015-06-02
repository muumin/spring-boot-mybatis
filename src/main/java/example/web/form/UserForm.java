package example.web.form;

import example.mybatis.domain.User;
import example.mybatis.enums.UserType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserForm implements Serializable {
    public interface Insert {
    }

    public interface Update {
    }

    @NotNull(groups = Insert.class, message = "loginIdは必須です")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", groups = {Insert.class, Update.class}, message = "loginIdは英数字です")
    @Length(min=1,max = 100, groups = {Insert.class}, message = "loginIdは{min}以上{max}以下です")
    private String loginId;

    @NotNull(groups = Insert.class, message = "passwordは必須です")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", groups = {Insert.class, Update.class}, message = "passwordは英数字です")
    @Length(min = 5, max = 100, groups = {Insert.class, Update.class}, message = "passwordは{min}以上{max}以下です")
    private String password;

    @NotNull(groups = Insert.class, message = "nameは必須です")
    @Length(min=1, max = 100, groups = {Insert.class, Update.class}, message = "nameは{min}以上{max}以下です")
    private String name;

    @NotNull(groups = Insert.class, message = "mailは必須です")
    @Length(min=1, max = 100, groups = {Insert.class, Update.class}, message = "mailは{min}以上{max}以下です")
    @Email(groups = {Insert.class, Update.class}, message = "mailのフォーマットが不正です")
    private String mail;

    @NotNull(groups = Insert.class, message = "userTypeは必須です")
    @Pattern(regexp = "^(8|12)$", groups = {Insert.class, Update.class}, message = "userTypeは8か12です")
    private String userType;

    public User getUser() {
        User user = new User();

        user.setLoginId(loginId);
        user.setEncodedPassword(password);
        user.setName(name);
        user.setMailAddress(mail);
        if (!StringUtils.isEmpty(userType)) {
            user.setUserType(UserType.fromCode(userType));
        }

        return user;
    }
}
