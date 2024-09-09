package com.sparta.hub.exception;

import com.sparta.commons.domain.response.FailedResponseBody;
import com.sparta.commons.domain.response.ResponseBody;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<FailedResponseBody> handleAlreadyDeletedException(AlreadyDeletedException e) {
        FailedResponseBody error = new FailedResponseBody(ErrorCode.ERROR_ALREADY_DELETED.getCode(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseBody handleEntityNotFoundException(EntityNotFoundException e) {
        return new FailedResponseBody("404", e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseBody handleIllegalStateException(IllegalStateException e) {
        return new FailedResponseBody("500", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailedResponseBody> handleException(Exception e) {
        FailedResponseBody error = new FailedResponseBody(ErrorCode.ERROR_DEFAULT.getCode(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
