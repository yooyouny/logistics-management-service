package com.sparta.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  // Common
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON_0001", "잘못된 입력 값입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_002", "서버 에러입니다."),

  // USER
  USER_CONFLICT(HttpStatus.CONFLICT, "USER_0001", "이미 존재하는 사용자입니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_0001", "존재하지 않는 사용자입니다."),

  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
