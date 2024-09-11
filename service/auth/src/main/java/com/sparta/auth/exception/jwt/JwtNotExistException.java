package com.sparta.auth.exception.jwt;


import com.sparta.auth.exception.ErrorCode;

public class JwtNotExistException extends JwtAuthenticationException {

  public JwtNotExistException() {
    super(ErrorCode.JWT_NOT_EXIST);
  }
}
