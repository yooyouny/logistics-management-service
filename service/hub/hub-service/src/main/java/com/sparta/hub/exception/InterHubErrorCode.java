package com.sparta.hub.exception;

import com.sparta.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum InterHubErrorCode implements ErrorCode {
  NOT_FOUND(HttpStatus.NOT_FOUND, "HUB_001", "해당 허브 간 이동정보가 존재하지 않습니다"),
  ALREADY_DELETED(HttpStatus.BAD_REQUEST, "HUB_002", "이미 삭제된 허브 간 이동정보입니다"),
  INVALID_ROUTE_SAME_START_END(HttpStatus.BAD_REQUEST, "HUB_003", "출발지와 목적지가 같습니다");

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
