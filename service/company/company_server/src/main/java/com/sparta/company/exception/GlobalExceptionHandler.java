package com.sparta.company.exception;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.commons.domain.exception.ErrorCode;
import com.sparta.commons.domain.response.FailedResponseBody;
import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.company.exception.AlreadyDeletedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class) // custom 에러
    public ResponseEntity<ResponseBody<Void>> handleServiceException(HttpServletRequest request,
        BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
            .body(new FailedResponseBody(errorCode.getCode(), errorCode.getMessage()));
    }

}
