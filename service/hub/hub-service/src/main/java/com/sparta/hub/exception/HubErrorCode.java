package com.sparta.hub.exception;

import com.sparta.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum HubErrorCode implements ErrorCode {
  NOT_FOUND(HttpStatus.NOT_FOUND, "HUB_001", "해당 허브가 존재하지 않습니다"),
  ALREADY_DELETED(HttpStatus.BAD_REQUEST, "HUB_002", "이미 삭제된 허브입니다")
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
