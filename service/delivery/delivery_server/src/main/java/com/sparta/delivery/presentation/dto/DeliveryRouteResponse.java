package com.sparta.delivery.presentation.dto;

import com.sparta.delivery.domain.model.DeliveryRoute;
import com.sparta.delivery.domain.model.State.RouteState;
import java.time.Duration;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryRouteResponse {
  private UUID deliveryRouteId;
  private int sequence;
  private UUID departureHubId;
  private UUID arrivalHubId;
  private RouteState routeState;
  private double estimatedDistance;
  private long estimatedElapsedTime;
  private Duration realElapsedTime;

  @Builder
  private DeliveryRouteResponse(
      UUID deliveryRouteId,
      int sequence,
      UUID departureHubId,
      UUID arrivalHubId,
      double estimatedDistance,
      long estimatedElapsedTime,
      Duration realElapsedTime,
      RouteState routeState) {
    this.deliveryRouteId = deliveryRouteId;
    this.sequence = sequence;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.estimatedDistance = estimatedDistance;
    this.estimatedElapsedTime = estimatedElapsedTime;
    this.realElapsedTime = realElapsedTime;
    this.routeState = routeState;
  }

  public static DeliveryRouteResponse fromEntity(DeliveryRoute route){
    return DeliveryRouteResponse.builder()
        .deliveryRouteId(route.getDeliveryRouteId())
        .sequence(route.getSequence())
        .departureHubId(route.getDepartureHubId())
        .arrivalHubId(route.getArrivalHubId())
        .routeState(route.getRouteState())
        .estimatedDistance(route.getEstimatedDistance())
        .estimatedElapsedTime(route.getEstimatedElapsedTime())
        .realElapsedTime(route.getRealElapsedTime())
        .build();
  }
}
