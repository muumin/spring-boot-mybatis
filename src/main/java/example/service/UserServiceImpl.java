package example.service;

import example.mybatis.domain.User;
import example.mybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        // IN句のサンプル
        userMapper.findIn(Arrays.asList(1, 2)).forEach(s -> log.debug("SELECT * FROM USERS WHERE (ID IN ?):{}", s));

        return userMapper.find();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String loginId) {
        return Optional.ofNullable(userMapper.findById(loginId)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteUser(String loginId) {
        Optional<User> user = Optional.ofNullable(userMapper.findById(loginId));
        userMapper.delete(user.orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public User addUser(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user.getVersion() == null) {
            user.setVersion(Optional.ofNullable(userMapper.findById(user.getLoginId())).map(User::getVersion).orElseThrow(EntityNotFoundException::new));
        }

        if (userMapper.update(user) == 0) {
            throw new ObjectOptimisticLockingFailureException(User.class, user);
        }

        return userMapper.findById(user.getLoginId());
    }
}
