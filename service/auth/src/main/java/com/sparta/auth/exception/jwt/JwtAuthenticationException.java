package com.sparta.auth.exception.jwt;

import com.sparta.auth.exception.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

  private ErrorCode errorCode;

  public JwtAuthenticationException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public JwtAuthenticationException(Throwable cause, ErrorCode errorCode) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }
}
