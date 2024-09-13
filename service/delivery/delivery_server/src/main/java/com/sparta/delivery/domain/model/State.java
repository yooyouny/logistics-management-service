package com.sparta.delivery.domain.model;

public class State {

  public enum DeliveryState {
    PENDING("배송대기"),
    REQUESTED("배송요청"),// 외부 API 전파없음
    IN_TRANSIT("배송중"),
    DELIVERED("배송완료"),
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
    OUT_FOR_DELIVERY("출고완료"),
    IN_TRANSIT_TO_COMPANY("업체배송중"),
    DELIVERED("배송완료");
    final String description;

    RouteState(String description) {
      this.description = description;
    }
  }
}
