package example.web.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResult {
    public enum Status {
        SUCCESS, WARNING, ERROR
    }

    private Status status = Status.SUCCESS;

    @Singular
    private List<String> messages;

    public BasicResult(BindingResult bindingResult) {
        Optional.ofNullable(bindingResult).ifPresent(b -> {
            if (b.hasErrors()) {
                setStatus(BasicResult.Status.ERROR);
                messages = new ArrayList<>();
                b.getAllErrors().forEach(s -> messages.add(s.getDefaultMessage()));
            }
        });
    }
}
