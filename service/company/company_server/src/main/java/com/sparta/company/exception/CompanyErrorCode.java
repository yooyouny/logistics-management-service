package com.sparta.company.exception;

import com.sparta.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CompanyErrorCode implements ErrorCode {
  NOT_FOUND(HttpStatus.NOT_FOUND, "COMPANY_001", "해당 업체가 존재하지 않습니다"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_0001", "존재하지 않는 사용자입니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "SECURITY_0002", "권한이 없습니다.")

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
