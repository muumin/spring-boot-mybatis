package example.service;

import example.mybatis.domain.User;
import example.web.result.BasicResult;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(String loginId);

    void deleteUser(String loginId);

    User addUser(User user);

    User updateUser(User user);
}
