package com.sparta.delivery.presentation.exception;

import com.sparta.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum DeliveryErrorCode implements ErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "common_error_001", "서버 내부 에러"),
  NOT_FOUND_DELIVERY(HttpStatus.NOT_FOUND, "delivery_error_001", "배송정보를 찾을 수 없습니다"),
  NOT_FOUND_HUB(HttpStatus.NOT_FOUND, "delivery_error_002", "관리허브정보를 찾을 수 없습니다"),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  @Override
  public int getStatus() {
    return 0;
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
