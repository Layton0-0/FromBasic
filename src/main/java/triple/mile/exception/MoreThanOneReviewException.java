package triple.mile.exception;

import triple.mile.response.ErrorEnum;

public class MoreThanOneReviewException extends CustomException{
    private static final long serialVersionUID = 8686451316866146416L;

    public MoreThanOneReviewException() {
        super(ErrorEnum.MORE_THAN_ONE_REVIEW);
    }
}
