package com.sparta.auth.exception;

import com.sparta.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
  // Common
  INVALID_INPUT_VALUE(400, "COMMON_0001", "잘못된 입력 값입니다."),
  INTERNAL_SERVER_ERROR(500, "COMMON_002", "서버 에러입니다."),

  // Security
  NEED_AUTHORIZED(401, "SECURITY_0001", "인증이 필요합니다."),
  ACCESS_DENIED(403, "SECURITY_0002", "권한이 없습니다."),
  JWT_EXPIRED(401, "SECURITY_0003", "JWT 토큰이 만료되었습니다."),
  JWT_INVALID(401, "SECURITY_0004", "JWT 토큰이 올바르지 않습니다."),
  JWT_NOT_EXIST(401, "SECURITY_0005", "JWT 토큰이 존재하지 않습니다."),

  // AUTH
  SIGN_IN_FAIL(401, "AUTH_0001", "로그인에 실패했습니다."),
  ;

  private final int status;
  private final String code;
  private final String message;
}
