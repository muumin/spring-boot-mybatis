package example.web;

import example.web.result.BasicResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public BasicResult handleEntityNotFoundException() {
        return BasicResult.builder().status(BasicResult.Status.WARNING).message("対象データがありませんでした").build();
    }

    /**
     * 楽観的ロック.
     *
     * @return エラーJson
     */
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseBody
    public BasicResult handleObjectOptimisticLockingFailureException() {
        return BasicResult.builder().status(BasicResult.Status.ERROR).message("対象データが更新済みです").build();
    }
}
