package com.sparta.user.domain.model.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ShippingManagerType {
  HUB_DELIVERY_AGENT("허브배송담당자"),
  COMPANY_DELIVERY_AGENT("업체배송담당자")
  ;

  private final String type;

  @JsonValue
  public String getType() {
    return type;
  }

  @JsonCreator
  public static ShippingManagerType fromString(String type) {
    for (ShippingManagerType shippingManagerType : ShippingManagerType.values()) {
      if (shippingManagerType.type.equals(type)) {
        return shippingManagerType;
      }
    }
    return null;
  }
}
