package com.sparta.order.domain.model;

public enum OrderState {
  CREATED("주문생성"),
  SHIPPED("배송중"),
  DELIVERED("배송완료"),
  CONFIRMED("주문확정"), // API order -> delivery state 전파
  CANCELLED("주문취소"); // API order -> OrderState 변경 및 삭제 진행

  final String description;

  OrderState(String description) {
    this.description = description;
  }
}
