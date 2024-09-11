package com.sparta.auth.exception.jwt;

import com.sparta.auth.exception.ErrorCode;

public class JwtTokenInvalidException extends JwtAuthenticationException {

  public JwtTokenInvalidException() {
    super(ErrorCode.JWT_INVALID);
  }

  public JwtTokenInvalidException(Throwable cause) {
    super(cause, ErrorCode.JWT_INVALID);
  }
}
