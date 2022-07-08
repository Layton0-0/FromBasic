package triple.mile.exception;

import triple.mile.response.ErrorEnum;

public class NotPlaceException extends CustomException{
    private static final long serialVersionUID = 6548778946654564635L;

    public NotPlaceException() {
        super(ErrorEnum.NOT_PLACE);
    }
}
