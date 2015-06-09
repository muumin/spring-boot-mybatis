package example.web;

import example.service.UserService;
import example.web.form.UserForm;
import example.web.result.BasicDataResult;
import example.web.result.BasicResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    BasicResult read() {
        return new BasicDataResult<>(userService.getUsers());
    }

    @RequestMapping(value = "/{loginId}", method = RequestMethod.GET)
    BasicResult read(@PathVariable String loginId) {
        return new BasicDataResult<>(userService.getUser(loginId));
        // 日付をLocalDateTimeで整形したいなら以下
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
//            log.debug("{}.create={}", user.getLoginId(), user.getCreated().format(formatter));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    BasicResult create(@Validated(UserForm.Insert.class) UserForm userForm, BindingResult bindingResult) {
        BasicResult result = new BasicResult(bindingResult);
        if (result.getStatus() == BasicResult.Status.ERROR) {
            return result;
        }

        return new BasicDataResult<>(userService.addUser(userForm.getUser()));
    }

    @RequestMapping(value = "/{loginId}", method = {RequestMethod.POST, RequestMethod.PUT})
    BasicResult update(@PathVariable Optional<String> loginId,
                       @Validated(UserForm.Update.class) UserForm userForm,
                       BindingResult bindingResult) {
        // Form > @PathVariableのようなので一回セット
        loginId.ifPresent(userForm::setLoginId);

        BasicResult result = new BasicResult(bindingResult);
        if (result.getStatus() == BasicResult.Status.ERROR) {
            return result;
        }

        return new BasicDataResult<>(userService.updateUser(userForm.getUser()));
    }

    @RequestMapping(value = "/{loginId}", method = RequestMethod.DELETE)
    BasicResult delete(@PathVariable String loginId) {
        userService.deleteUser(loginId);
        return new BasicResult();
    }
}
