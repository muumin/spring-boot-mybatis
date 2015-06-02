package example.service;

import example.mybatis.domain.User;
import example.web.result.BasicResult;

public interface UserService {
    BasicResult getUsers();

    BasicResult getUser(String id);

    BasicResult deleteUser(String id);

    BasicResult addUser(User user);

    BasicResult updateUser(User user);
}
