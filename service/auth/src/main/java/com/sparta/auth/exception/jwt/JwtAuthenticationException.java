package com.sparta.auth.exception.jwt;

import com.sparta.auth.exception.AuthErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

  private AuthErrorCode errorCode;

  public JwtAuthenticationException(AuthErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public JwtAuthenticationException(Throwable cause, AuthErrorCode errorCode) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }
}
