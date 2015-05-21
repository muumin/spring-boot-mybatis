package example.mybatis.handler;


import example.mybatis.enums.UserType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(UserType.class)
public class UserTypeHandler extends BaseTypeHandler<UserType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UserType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public UserType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String ret = rs.getString(columnName);
        if (ret != null) {
            return UserType.fromCode(ret);
        }
        return null;
    }

    @Override
    public UserType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String ret = rs.getString(columnIndex);
        if (ret != null) {
            return UserType.fromCode(ret);
        }
        return null;
    }

    @Override
    public UserType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String ret = cs.getString(columnIndex);
        if (ret != null) {
            return UserType.fromCode(ret);
        }
        return null;
    }
}
