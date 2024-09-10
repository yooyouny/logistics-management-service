package com.sparta.auth.exception.jwt;

import com.sparta.auth.exception.ErrorCode;
import lombok.Getter;

@Getter
public class JwtTokenExpiredException extends JwtAuthenticationException {

  public JwtTokenExpiredException(Throwable cause) {
    super(cause, ErrorCode.JWT_EXPIRED);
  }

}
