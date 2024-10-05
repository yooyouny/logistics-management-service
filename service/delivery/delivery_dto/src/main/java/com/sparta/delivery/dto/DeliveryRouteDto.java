package com.sparta.delivery.dto;

import java.time.Duration;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryRouteDto {
  private UUID deliveryRouteId;
  private int sequence;
  private UUID departureHubId;
  private UUID arrivalHubId;
  private double estimatedDistance;
  private long estimatedElapsedTime;
  private Duration realElapsedTime;
  private String routeState;

  @Builder
  public DeliveryRouteDto(
      UUID deliveryRouteId,
      int sequence,
      UUID departureHubId,
      UUID arrivalHubId,
      double estimatedDistance,
      long estimatedElapsedTime,
      Duration realElapsedTime,
      String routeState) {
    this.deliveryRouteId = deliveryRouteId;
    this.sequence = sequence;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.estimatedDistance = estimatedDistance;
    this.estimatedElapsedTime = estimatedElapsedTime;
    this.realElapsedTime = realElapsedTime;
    this.routeState = routeState;
  }
}
