package com.sparta.order.presentation.exception;

import com.sparta.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum OrderErrorCode implements ErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "common_error_001", "서버 내부 에러"),
  NO_AUTH(HttpStatus.UNAUTHORIZED, "common_error_002", "인증정보가 없습니다"),
  NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "order_error_001", "주문정보를 찾을 수 없습니다"),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  @Override
  public int getStatus() {
    return status.value();
  }

  public HttpStatus getHttpStatus() {
    return status;
  }

  @Override
  public String getCode() {
    return null;
  }

  @Override
  public String getMessage() {
    return null;
  }
}
