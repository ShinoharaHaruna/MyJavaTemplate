package cc.icooper.majavatemplate.core.data.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public static Result success() {
        return new Result(200, "success", null);
    }

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result fail(String message) {
        return new Result(500, message, null);
    }

    public static Result fail(String message, Object data) {
        return new Result(500, message, data);
    }
}
