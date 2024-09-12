package com.sparta.user.exception;

import com.sparta.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
  // Common
  INVALID_INPUT_VALUE(400, "COMMON_0001", "잘못된 입력 값입니다."),
  INTERNAL_SERVER_ERROR(500, "COMMON_002", "서버 에러입니다."),

  // Security
  NEED_AUTHORIZED(401, "SECURITY_0001", "인증이 필요합니다."),
  ACCESS_DENIED(403, "SECURITY_0002", "권한이 없습니다."),

  // User
  USER_CONFLICT(409, "USER_0001", "이미 존재하는 사용자입니다."),
  USER_NOT_FOUND(404, "USER_0001", "존재하지 않는 사용자입니다."),

  ;

  private final int status;
  private final String code;
  private final String message;
}
