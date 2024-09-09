package com.sparta.order.domain.model;

public class State {
  public enum OrderState {
    PENDING("주문대기"),
    CONFIRMED("주문완료"),
    SHIPPED("배송중"),
    DELIVERED("배송완료"),
    CANCELLED("주문취소");

    final String description;

    OrderState(String description) {
      this.description = description;
    }
  }

  public enum DeliveryState {
    PENDING("배송대기중"),
    IN_TRANSIT_TO_HUB("허브이동중"),
    AT_ARRIVAL_HUB("목적지허브도착"),
    OUT_FOR_DELIVERY("출고완료"),
    IN_TRANSIT_TO_COMPANY("업체배송중"),
    CONFIRMED("배송확정");

    final String description;

    DeliveryState(String description) {
      this.description = description;
    }
  }

  public enum RouteState {
    PENDING("허브이동대기중"),
    IN_TRANSIT_TO_HUB("허브이동중"),
    AT_ARRIVAL_HUB("목적지허브도착"),
    IN_TRANSIT_TO_COMPANY("업체배송중"),
    DELIVERED("배송완료");
    final String description;

    RouteState(String description) {
      this.description = description;
    }
  }
}
