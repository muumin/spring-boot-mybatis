package example.mybatis.mapper;

import example.mybatis.domain.User;
import example.mybatis.mapper.provider.UsersProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

public interface UserMapper {

    @SelectProvider(type = UsersProvider.class, method = "select")
        // Mapper.xmlに定義したresultMapを使用する場合
//    @ResultMap("UserResult")
        // annotationで指定する場合
//    @Results({
//            @Result(property = "id", column = "id", id = true),
//            @Result(property="loginId", column="LOGIN_ID"),
//            @Result(property="encodedPassword", column="ENCODED_PASSWORD")
//    })
    List<User> selectAll();

    @SelectProvider(type = UsersProvider.class, method = "select")
    User selectOne(String id);

    @SelectProvider(type = UsersProvider.class, method = "selectIn")
    List<User> selectIn(@Param("list") List<Integer> list);

    @InsertProvider(type = UsersProvider.class, method = "insert")
    // 以下のannotationでinsert後のid(IDENTITY)がObjectに設定される
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    // 単純なSQLならannotationに直接定義可能。他に@Selectや@Insertなどがある
    @Delete("DELETE FROM USERS WHERE LOGIN_ID=#{id}")
    int delete(String id);

    @UpdateProvider(type = UsersProvider.class, method = "update")
    int update(User user);
}
