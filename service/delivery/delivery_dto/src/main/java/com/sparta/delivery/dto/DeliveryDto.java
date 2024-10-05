package com.sparta.delivery.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryDto {
  private UUID deliveryId;
  private UUID departureHubId;
  private UUID arrivalHubId;
  private String deliveryState;
  private String shippingAddress;
  private UUID shippingManagerId;
  private String shippingManagerSlackId;
  private List<DeliveryRouteDto> routes;
  private long estimatedElapsedTime;
  private double estimatedDistance;
  private LocalDateTime shippingStartDate;
  private LocalDateTime shippingEndDate;

  @Builder
  public DeliveryDto(
      UUID deliveryId,
      UUID departureHubId,
      UUID arrivalHubId,
      String deliveryState,
      String shippingAddress,
      UUID shippingManagerId,
      String shippingManagerSlackId,
      List<DeliveryRouteDto> routes,
      long estimatedElapsedTime,
      double estimatedDistance,
      LocalDateTime shippingStartDate,
      LocalDateTime shippingEndDate) {
    this.deliveryId = deliveryId;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.deliveryState = deliveryState;
    this.shippingAddress = shippingAddress;
    this.shippingManagerId = shippingManagerId;
    this.shippingManagerSlackId = shippingManagerSlackId;
    this.routes = routes;
    this.estimatedElapsedTime = estimatedElapsedTime;
    this.estimatedDistance = estimatedDistance;
    this.shippingStartDate = shippingStartDate;
    this.shippingEndDate = shippingEndDate;
  }
}
