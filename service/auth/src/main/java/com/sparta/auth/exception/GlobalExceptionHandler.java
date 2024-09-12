package com.sparta.auth.exception;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.commons.domain.exception.ErrorCode;
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

  @ExceptionHandler(FeignClientException.class) // custom 에러
  public ResponseEntity<ResponseBody<Void>> handleFeignClientException(HttpServletRequest request,
      FeignClientException e) {
    return ResponseEntity.status(e.getHttpStatus())
        .body(new FailedResponseBody(e.getCode(), e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class) // Valid
  public ResponseEntity<ResponseBody<Void>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

    log.error("MethodArgumentNotValidException : {}", errorMessage);
    return ResponseEntity.badRequest()
        .body(new FailedResponseBody(AuthErrorCode.INVALID_INPUT_VALUE.getCode(), errorMessage));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseBody<Void>> handleException(HttpServletRequest request,
      Exception e) {
    log.error("Exception : {}", e.getMessage());
    return ResponseEntity.internalServerError()
        .body(new FailedResponseBody(AuthErrorCode.INTERNAL_SERVER_ERROR.getCode(),
            AuthErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
  }
}
