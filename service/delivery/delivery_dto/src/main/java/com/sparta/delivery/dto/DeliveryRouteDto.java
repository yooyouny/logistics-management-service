package com.sparta.delivery.dto;

import java.time.Duration;
import java.util.UUID;

public class DeliveryRouteDto {
  private UUID deliveryRouteId;
  private int sequence;
  private UUID departureHubId;
  private UUID arrivalHubId;
  private double estimatedDistance;
  private long estimatedElapsedTime;
  private Integer realDistance;
  private Duration realElapsedTime;
  private String routeState;

  public DeliveryRouteDto(UUID deliveryRouteId, int sequence, UUID departureHubId,
      UUID arrivalHubId, double estimatedDistance, long estimatedElapsedTime, Integer realDistance,
      Duration realElapsedTime, String routeState) {
    this.deliveryRouteId = deliveryRouteId;
    this.sequence = sequence;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.estimatedDistance = estimatedDistance;
    this.estimatedElapsedTime = estimatedElapsedTime;
    this.realDistance = realDistance;
    this.realElapsedTime = realElapsedTime;
    this.routeState = routeState;
  }
}
