package com.sparta.commons.domain.exception;

public interface ErrorCode {
  int getStatus();

  String getCode();

  String getMessage();
}
