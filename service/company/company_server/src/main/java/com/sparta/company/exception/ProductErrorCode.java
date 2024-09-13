package com.sparta.company.exception;

import com.sparta.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ProductErrorCode implements ErrorCode {
  OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_0002", "재고가 부족합니다."),
  ORDER_QUANTITY_EXCEEDS_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_0003", "주문 수량이 재고 수량보다 많습니다."),
  NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_0001", "해당 상품이 존재하지 않습니다")
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  @Override
  public int getStatus() {
    return status.value();
  }

  public HttpStatus getHttpStatus() {
    return status;
  }

  @Override
  public String getCode() {
    return null;
  }

  @Override
  public String getMessage() {
    return null;
  }
}
