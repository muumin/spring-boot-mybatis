package example.mybatis.enums;

/**
 * これは01から11、99など間の抜けた業務コードのパターンを想定。<br />
 * 0から付け加えるだけならenumだけでTypeHandlerも作成不要。
 */
public enum UserType {
    ADMIN("8"),
    USER("12");

    String code;

    UserType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static UserType fromCode(String code) {
        for (UserType ut : UserType.values()) {
            if (ut.code.equals(code)) {
                return ut;
            }
        }
        throw new IllegalArgumentException("code = " + code);
    }
}
