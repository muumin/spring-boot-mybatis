package example.web;

import example.mybatis.domain.User;
import example.service.UserService;
import example.web.form.UserForm;
import example.web.result.BasicResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<User> read() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/{loginId}", method = RequestMethod.GET)
    User read(@PathVariable String loginId) {
        User user = userService.getUser(loginId);
        if (user != null) {
            // 日付をLocalDateTimeで整形したいなら以下
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
            log.debug("{}.create={}", user.getLoginId(), user.getCreated().format(formatter));
        }

        return user;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    BasicResult create(@Validated(UserForm.Insert.class) UserForm userForm, BindingResult bindingResult) {
        BasicResult result = getBasicResult(bindingResult);
        if (result.getStatus() == BasicResult.Status.ERROR) {
            return result;
        }
        User user = userForm.getUser();
        userService.addUser(user);
        result.addMessage(String.format("id:%d", user.getId()));

        return result;
    }

    @RequestMapping(value = "/{loginId}", method = {RequestMethod.POST, RequestMethod.PUT})
    BasicResult update(@PathVariable String loginId,
                       @Validated(UserForm.Update.class) UserForm userForm, BindingResult bindingResult) {
        BasicResult result = getBasicResult(bindingResult);
        if (result.getStatus() == BasicResult.Status.ERROR) {
            return result;
        }

        User user = userForm.getUser();
        user.setLoginId(loginId);
        int count = userService.updateUser(user);

        if (count == 0) {
            result.setStatus(BasicResult.Status.WARNING);
            result.addMessage("対象データがありませんでした");
        }

        return result;
    }

    @RequestMapping(value = "/{loginId}", method = RequestMethod.DELETE)
    BasicResult delete(@PathVariable String loginId) {
        BasicResult result = getBasicResult();
        int count = userService.deleteUser(loginId);

        if (count == 0) {
            result.setStatus(BasicResult.Status.WARNING);
            result.addMessage("対象データがありませんでした");
        }
        return result;
    }

    private BasicResult getBasicResult() {
        return getBasicResult(null);
    }

    private BasicResult getBasicResult(BindingResult bindingResult) {
        BasicResult result = new BasicResult();

        if (bindingResult != null && bindingResult.hasErrors()) {
            result.setStatus(BasicResult.Status.ERROR);
            bindingResult.getAllErrors().forEach(s -> result.addMessage(s.getDefaultMessage()));
            return result;
        }

        return result;
    }

}
