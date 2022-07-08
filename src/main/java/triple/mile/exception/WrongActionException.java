package triple.mile.exception;

import triple.mile.response.ErrorEnum;

public class WrongActionException extends CustomException{
    private static final long serialVersionUID = 6541841624864528674L;

    public WrongActionException() {
        super(ErrorEnum.WRONG_ACTION);
    }
}
