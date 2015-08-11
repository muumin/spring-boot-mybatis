package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

public class V01_00_01__Insert_Data implements SpringJdbcMigration {
    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        String sql = "INSERT INTO USERS(LOGIN_ID,ENCODED_PASSWORD,NAME,MAIL_ADDRESS,USER_TYPE) VALUES (?,?,?,?,?)";
        List<String> lines = Arrays.asList(
                "TEST1,TEST1,山田太郎,tarou@example.com,8",
                "TEST2,TEST2,山田花子,hanako@example.com,12");

        lines.forEach(line -> jdbcTemplate.update(sql, (Object[]) line.split(",")));
    }
}
