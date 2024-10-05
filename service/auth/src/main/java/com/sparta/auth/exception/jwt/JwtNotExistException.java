package com.sparta.auth.exception.jwt;

import com.sparta.auth.exception.AuthErrorCode;

public class JwtNotExistException extends JwtAuthenticationException {

  public JwtNotExistException() {
    super(AuthErrorCode.JWT_NOT_EXIST);
  }
}
