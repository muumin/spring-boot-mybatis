package example.mybatis.enums;

import java.util.stream.Stream;

/**
 * これは01から11、99など間の抜けた業務コードのパターンを想定。<br />
 * 0からならenum定数だけ定義して定義ファイルにEnumOrdinalTypeHandlerを定義ればTypeHandlerも作成不要。<br/>
 * 定義方法はsrc/main/resources/mybatis-config.xmlを参照。
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
        return Stream.of(UserType.values())
                .filter(ut -> ut.code.equals(code))
                .findFirst()
                .orElse(null);
    }
}
