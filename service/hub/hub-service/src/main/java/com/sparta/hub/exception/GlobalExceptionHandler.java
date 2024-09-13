package com.sparta.hub.exception;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.commons.domain.response.FailedResponseBody;
import com.sparta.commons.domain.response.ResponseBody;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class) // custom 에러
    public ResponseEntity<ResponseBody<Void>> handleServiceException(HttpServletRequest request,
        BusinessException e) {
        com.sparta.commons.domain.exception.ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
            .body(new FailedResponseBody(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<Void>> handleException(HttpServletRequest request,
        Exception e) {
        log.error("Exception : {}", e.getMessage());
        return ResponseEntity.internalServerError()
            .body(new FailedResponseBody(HubErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                HubErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
