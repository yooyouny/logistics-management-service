package com.sparta.order.domain.model;

public enum OrderState {
    CREATED("주문생성"),
    SHIPPED("배송중"),
    DELIVERED("배송완료"),
    CANCELLED("주문취소");

    final String description;

    OrderState(String description) {
      this.description = description;
    }
}
