package triple.mile.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import triple.mile.response.ErrorEnum;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1764536546345648897L;

    private ErrorEnum errorCode;

    public CustomException(ErrorEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
