package triple.mile.exception;

import triple.mile.response.ErrorEnum;

public class WrongTypeException extends CustomException{
    private static final long serialVersionUID = 3554613546531310236L;

    public WrongTypeException() {
        super(ErrorEnum.WRONG_TYPE);
    }
}
