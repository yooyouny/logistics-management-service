package com.sparta.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  // Common
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON_0001", "잘못된 입력 값입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_002", "서버 에러입니다."),

  // Security
  NEED_AUTHORIZED(HttpStatus.UNAUTHORIZED, "SECURITY_0001", "인증이 필요합니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "SECURITY_0002", "권한이 없습니다."),
  JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "SECURITY_0003", "JWT 토큰이 만료되었습니다."),
  JWT_INVALID(HttpStatus.UNAUTHORIZED, "SECURITY_0004", "JWT 토큰이 올바르지 않습니다."),
  JWT_NOT_EXIST(HttpStatus.UNAUTHORIZED, "SECURITY_0005", "JWT 토큰이 존재하지 않습니다."),

  // AUTH
  SIGN_IN_FAIL(HttpStatus.UNAUTHORIZED, "AUTH_0001", "로그인에 실패했습니다."),

  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
