package com.sparta.auth.exception.jwt;

import com.sparta.auth.exception.AuthErrorCode;
import lombok.Getter;

@Getter
public class JwtTokenExpiredException extends JwtAuthenticationException {

  public JwtTokenExpiredException(Throwable cause) {
    super(cause, AuthErrorCode.JWT_EXPIRED);
  }

}
