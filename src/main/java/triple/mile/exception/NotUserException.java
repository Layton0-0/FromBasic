package triple.mile.exception;

import triple.mile.response.ErrorEnum;

public class NotUserException extends CustomException{
    private static final long serialVersionUID = 6673468441653864465L;

    public NotUserException() {
        super(ErrorEnum.NOT_USER);
    }
}
