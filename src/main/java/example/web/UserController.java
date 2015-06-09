package example.web;

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

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    BasicResult read() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/{loginId}", method = RequestMethod.GET)
    BasicResult read(@PathVariable String loginId) {
        return userService.getUser(loginId);
        // 日付をLocalDateTimeで整形したいなら以下
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
//            log.debug("{}.create={}", user.getLoginId(), user.getCreated().format(formatter));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    BasicResult create(@Validated(UserForm.Insert.class) UserForm userForm, BindingResult bindingResult) {
        BasicResult result = getBasicResult(bindingResult);
        if (result.getStatus() == BasicResult.Status.ERROR) {
            return result;
        }

        return userService.addUser(userForm.getUser());
    }

    @RequestMapping(value = "/{loginId}", method = {RequestMethod.POST, RequestMethod.PUT})
    BasicResult update(@PathVariable String loginId,
                       @Validated(UserForm.Update.class) UserForm userForm,
                       BindingResult bindingResult) {
        if (loginId != null) {
            userForm.setLoginId(loginId);
        }

        BasicResult result = getBasicResult(bindingResult);
        if (result.getStatus() == BasicResult.Status.ERROR) {
            return result;
        }

        return userService.updateUser(userForm.getUser());
    }

    @RequestMapping(value = "/{loginId}", method = RequestMethod.DELETE)
    BasicResult delete(@PathVariable String loginId) {
        return userService.deleteUser(loginId);
    }

    private BasicResult getBasicResult(BindingResult bindingResult) {
        BasicResult result = new BasicResult();

        if (bindingResult != null && bindingResult.hasErrors()) {
            result.setStatus(BasicResult.Status.ERROR);
            bindingResult.getAllErrors().forEach(s -> result.addMessage(s.getDefaultMessage()));
        }

        return result;
    }
}
