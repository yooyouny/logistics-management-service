package com.sparta.auth.exception;

import lombok.Getter;

@Getter
public class FeignClientException extends RuntimeException {

  private final int httpStatus;
  private final String code;

  public FeignClientException(int httpStatus, String code,  String errorMessage) {
    super(errorMessage);
    this.httpStatus = httpStatus;
    this.code = code;
  }
}
