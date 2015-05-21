package example.service;

import example.mybatis.domain.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(String id);

    int deleteUser(String id);

    int addUser(User user);

    int updateUser(User user);
}
