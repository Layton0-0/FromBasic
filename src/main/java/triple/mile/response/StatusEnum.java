package triple.mile.response;

import lombok.Getter;

@Getter
public enum StatusEnum {
    OK(200, "ok"),
    NOT_FOUND(400, "not found"),
    BAD_REQUEST(500, "bad request");

    private int code;
    private String message;

    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
