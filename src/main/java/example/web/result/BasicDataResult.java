package example.web.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BasicDataResult<T> extends BasicResult {
    private T data;
}
