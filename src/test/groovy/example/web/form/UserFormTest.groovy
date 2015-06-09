package example.web.form

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation

class UserFormTest extends Specification {
    @Shared
    def validator = Validation.buildDefaultValidatorFactory().getValidator()
    def userForm = UserForm.builder()
            .loginId("loginId")
            .password("password")
            .name("name")
            .mail("mail@example.com")
            .userType("ADMIN").build()

    @Unroll
    def "loginIdが#commentのvalidationテスト"() {
        when:
        userForm.setLoginId(value)
        def constraintValidation = validator.validate(userForm, UserForm.Insert.class)

        then: "エラーメッセージ数があっているか"
        constraintValidation.size() == message.size()

        and: "エラーメッセージが含まれているか"
        if (!message.isEmpty()) {
            constraintValidation.each {
                assert message.contains(it.getMessage())
            }
        }

        where:
        // @formatter:off
        comment        | value      | message
        "null"         | null       | ["loginIdは必須です"]
        "空文字"       | ""         | ["loginIdは必須です"]
        "空白文字"     | " "        | ["loginIdは英数字です"]
        "全角空白文字" | "　"       | ["loginIdは英数字です"]
        "全角文字"     | "あ"       | ["loginIdは英数字です"]
        "英字1文字"    | "a"        | []
        "数字1文字"    | "1"        | []
        "英字100文字"  | "a" * 100  | []
        "英字101文字"  | "a" * 101  | ["loginIdは100以下です"]
        // @formatter:on
    }
}
