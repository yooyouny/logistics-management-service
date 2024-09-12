package com.sparta.user.exception;

import com.sparta.commons.domain.response.FailedResponseBody;
import com.sparta.commons.domain.response.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class) // custom 에러
  public ResponseEntity<ResponseBody<Void>> handleServiceException(HttpServletRequest request,
      BusinessException e) {
    ErrorCode errorCode = e.getErrorCode();
    return ResponseEntity.status(errorCode.getStatus())
        .body(new FailedResponseBody(errorCode.getCode(), errorCode.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class) // Valid
  public ResponseEntity<ResponseBody<Void>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

    log.error("MethodArgumentNotValidException : {}", errorMessage);
    return ResponseEntity.badRequest()
        .body(new FailedResponseBody(ErrorCode.INVALID_INPUT_VALUE.getCode(), errorMessage));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseBody<Void>> handleException(HttpServletRequest request,
      Exception e) {
    log.error("Exception : {}", e.getMessage());
    return ResponseEntity.internalServerError()
        .body(new FailedResponseBody(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
            ErrorCode.INVALID_INPUT_VALUE.getMessage()));
  }
}
