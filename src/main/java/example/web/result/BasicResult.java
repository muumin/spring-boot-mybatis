package example.web.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BasicResult {
    public enum Status {
        SUCCESS, WARNING, ERROR
    }

    private Status status = Status.SUCCESS;
    private List<String> messages = new ArrayList<>();

    public void addMessage(String msg) {
        messages.add(msg);
    }

}
