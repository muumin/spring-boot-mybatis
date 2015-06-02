package example.service;

import example.mybatis.domain.User;
import example.mybatis.mapper.UserMapper;
import example.web.result.BasicDataResult;
import example.web.result.BasicResult;
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
    public BasicResult getUsers() {
        // IN句のサンプル
        userMapper.selectIn(Arrays.asList(1, 2)).forEach(s -> log.debug("SELECT * FROM USERS WHERE (ID IN ?):{}", s));

        BasicDataResult<List<User>> ret = new BasicDataResult<>();
        ret.setData(userMapper.selectAll());
        return ret;
    }

    @Override
    public BasicResult getUser(String id) {
        User user = userMapper.selectOne(id);
        if (user == null) {
            BasicResult ret = new BasicResult();
            ret.setStatus(BasicResult.Status.ERROR);
            return ret;
        } else {
            BasicDataResult<User> ret = new BasicDataResult<>();
            ret.setData(user);
            return ret;
        }
    }

    @Override
    @Transactional
    public BasicResult deleteUser(String id) {
        BasicResult result = new BasicResult();
        int count = userMapper.delete(id);

        if (count == 0) {
            result.setStatus(BasicResult.Status.WARNING);
            result.addMessage("対象データがありませんでした");
        }

        return result;
    }

    @Override
    @Transactional
    public BasicResult addUser(User user) {
        userMapper.insert(user);

        BasicDataResult<User> dataResult = new BasicDataResult<>();
        dataResult.setData(user);
        dataResult.addMessage(String.format("id:%d", user.getId()));

        return dataResult;
    }

    @Override
    @Transactional
    public BasicResult updateUser(User user) {
        int count = userMapper.update(user);

        BasicDataResult<User> dataResult = new BasicDataResult<>();
        dataResult.setData(user);
        if (count == 0) {
            dataResult.setStatus(BasicResult.Status.WARNING);
            dataResult.addMessage("対象データがありませんでした");
        }
        return dataResult;
    }
}
