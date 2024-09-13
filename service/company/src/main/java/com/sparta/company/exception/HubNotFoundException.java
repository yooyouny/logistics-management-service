package com.sparta.company.exception;

public class HubNotFoundException extends RuntimeException{

  public HubNotFoundException(String message) {
    super(message);
  }
}
