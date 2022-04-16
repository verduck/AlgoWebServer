package kr.ac.jj.algo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {RestException.class})
    protected ResponseEntity<ErrorResponse> handleRestException(RestException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(new ErrorResponse(e.getErrorCode()));
    }
}
