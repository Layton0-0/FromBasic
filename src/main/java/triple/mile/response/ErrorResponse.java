package triple.mile.response;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String code;
    private int status;
    private String message;

    public ErrorResponse(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
