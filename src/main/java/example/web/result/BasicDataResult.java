package example.web.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class BasicDataResult<T> extends BasicResult {
    private T data;

    public BasicDataResult(T t) {
        data = t;
    }
}
