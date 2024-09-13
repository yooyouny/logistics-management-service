package com.sparta.company.exception;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.commons.domain.response.FailedResponseBody;
import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.company.exception.AlreadyDeletedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class) // custom 에러
    public ResponseEntity<ResponseBody<Void>> handleServiceException(HttpServletRequest request,
        BusinessException e) {
        com.sparta.commons.domain.exception.ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
            .body(new FailedResponseBody(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<FailedResponseBody> handleAlreadyDeletedException(AlreadyDeletedException e) {
        FailedResponseBody error = new FailedResponseBody(ErrorCode.ERROR_ALREADY_DELETED.getCode(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<FailedResponseBody> handleEntityNotFoundException(EntityNotFoundException e) {
        FailedResponseBody error = new FailedResponseBody(ErrorCode.ERROR_EMPTY_DATA.getCode(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<FailedResponseBody> handleIllegalStateException(IllegalStateException e) {
        FailedResponseBody error = new FailedResponseBody(ErrorCode.ERROR_INVALID_STATE.getCode(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailedResponseBody> handleException(Exception e) {
        FailedResponseBody error = new FailedResponseBody(ErrorCode.ERROR_DEFAULT.getCode(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
