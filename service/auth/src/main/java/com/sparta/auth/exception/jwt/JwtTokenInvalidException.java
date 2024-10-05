package com.sparta.auth.exception.jwt;

import com.sparta.auth.exception.AuthErrorCode;

public class JwtTokenInvalidException extends JwtAuthenticationException {

  public JwtTokenInvalidException() {
    super(AuthErrorCode.JWT_INVALID);
  }

  public JwtTokenInvalidException(Throwable cause) {
    super(cause, AuthErrorCode.JWT_INVALID);
  }
}
