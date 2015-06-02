package example.web.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class BasicDataResult<T> extends BasicResult {
    private T data;
}
