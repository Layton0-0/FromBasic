package triple.mile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import triple.mile.exception.CustomException;
import triple.mile.response.ErrorEnum;
import triple.mile.response.ErrorResponse;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandler(CustomException e) {
        ErrorEnum errorCode = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getStatus(), errorCode.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorCode.getStatus()));
    }

}
