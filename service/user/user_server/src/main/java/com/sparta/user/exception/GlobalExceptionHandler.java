package com.sparta.user.exception;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.commons.domain.exception.ErrorCode;
import com.sparta.commons.domain.response.FailedResponseBody;
import com.sparta.commons.domain.response.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class) // custom 에러
  public ResponseEntity<ResponseBody<Void>> handleServiceException(
      HttpServletRequest request, BusinessException e) {
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
        .body(new FailedResponseBody(UserErrorCode.INVALID_INPUT_VALUE.getCode(), errorMessage));
  }

  @ExceptionHandler(AccessDeniedException.class) // 403 권한 관련 예외
  public ResponseEntity<ResponseBody<Void>> handleAccessDeniedException(AccessDeniedException e) {
    log.error("AccessDeniedException : {}", e.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            new FailedResponseBody(
                UserErrorCode.ACCESS_DENIED.getCode(), UserErrorCode.ACCESS_DENIED.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseBody<Void>> handleException(
      HttpServletRequest request, Exception e) {
    log.error("Exception : {}", e.getMessage());
    return ResponseEntity.internalServerError()
        .body(
            new FailedResponseBody(
                UserErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                UserErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
  }
}
