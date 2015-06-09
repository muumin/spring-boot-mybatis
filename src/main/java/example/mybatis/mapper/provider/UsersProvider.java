package example.mybatis.mapper.provider;

import example.mybatis.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * mybatisのSQLビルダー.<br />
 * <p>
 * https://mybatis.github.io/mybatis-3/ja/statement-builders.html
 */
public class UsersProvider {
    private static final String TABLE_NAME = "USERS";

    public String insert() {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME);
            VALUES("LOGIN_ID", "#{loginId}");
            VALUES("ENCODED_PASSWORD", "#{encodedPassword}");
            VALUES("NAME", "#{name}");
            VALUES("MAIL_ADDRESS", "#{mailAddress}");
            VALUES("USER_TYPE", "#{userType.code}");
        }}.toString();
    }

    public String find(final String loginId) {
        return new SQL() {{
            SELECT("*");
            FROM(TABLE_NAME);
            if (!StringUtils.isEmpty(loginId)) {
                WHERE("LOGIN_ID = #{id}");
                // 条件を追加すると
                // WHERE("LOGIN_ID = #{id}");
                // AND();
                // WHERE("LOGIN_ID = #{id}");

                // 以下のSQLが発行される
                // WHEREを続けるとその括弧内に追加されANDやORを挟むと別括弧で区切られる
                // SELECT * FROM USERS WHERE (LOGIN_ID = ? AND LOGIN_ID = ?) AND (LOGIN_ID = ?)
            }
        }}.toString();
    }

    public String update(final User user) {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            if (!StringUtils.isEmpty(user.getEncodedPassword())) {
                SET("ENCODED_PASSWORD = #{encodedPassword}");
            }
            if (!StringUtils.isEmpty(user.getName())) {
                SET("NAME = #{name}");
            }
            if (!StringUtils.isEmpty(user.getMailAddress())) {
                SET("MAIL_ADDRESS = #{mailAddress}");
            }
            if (user.getUserType() != null) {
                SET("USER_TYPE = #{userType}");
            }
            SET("VERSION = VERSION +1");
            SET("UPDATED = CURRENT_TIMESTAMP");

            WHERE("LOGIN_ID = #{loginId}");
            WHERE("VERSION = #{version}");
        }}.toString();
    }

    // IN句だと面倒だがこんな感じ？
    @SuppressWarnings("unchecked")
    public String findIn(Map<String, Object> map) {
        List<Integer> list = (List<Integer>) map.get("list");
        StringJoiner joiner = new StringJoiner(",", "(", ")");
        list.forEach(s -> joiner.add(s.toString()));

        return new SQL() {{
            SELECT("*");
            FROM(TABLE_NAME);
            WHERE("ID IN " + joiner.toString());
        }}.toString();
    }
}
