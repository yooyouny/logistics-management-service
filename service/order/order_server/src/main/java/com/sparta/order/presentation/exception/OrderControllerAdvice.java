package com.sparta.order.presentation.exception;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.commons.domain.response.FailedResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "OrderControllerAdvice")
public class OrderControllerAdvice {
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> businessExceptionHandler(BusinessException e){
    OrderErrorCode errorCode = (OrderErrorCode) e.getErrorCode();
    log.error("Error occurs in OrderServer : {}", e.getErrorCode());
    return ResponseEntity.status(errorCode.getHttpStatus())
        .body(new FailedResponseBody(errorCode.getCode(), errorCode.getMessage()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
    log.error("Error occurs in OrderServer : {}", e.getMessage());
    return ResponseEntity.status(OrderErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(new FailedResponseBody(OrderErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage()));
  }
}
