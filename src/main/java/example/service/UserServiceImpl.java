package example.service;

import example.mybatis.domain.User;
import example.mybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        // IN句のサンプル
        userMapper.selectIn(Arrays.asList(1, 2)).forEach(s -> log.debug("SELECT * FROM USERS WHERE (ID IN ?):{}", s));
        return userMapper.selectAll();
    }

    @Override
    public User getUser(String id) {
        return userMapper.selectOne(id);
    }

    @Override
    @Transactional
    public int deleteUser(String id) {
        return userMapper.delete(id);
    }

    @Override
    @Transactional
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    @Transactional
    public int updateUser(User user) {
        return userMapper.update(user);
    }
}
