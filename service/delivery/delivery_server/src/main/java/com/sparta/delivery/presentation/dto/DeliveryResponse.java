package com.sparta.delivery.presentation.dto;

import com.sparta.delivery.domain.model.Delivery;
import com.sparta.delivery.domain.model.State.DeliveryState;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryResponse {
  private UUID deliveryId;
  private UUID orderId;
  private UUID departureHubId;
  private UUID arrivalHubId;
  private DeliveryState state;
  private String shippingAddress;
  private UUID shippingManagerId;
  private String shippingManagerSlackId;
  private List<DeliveryRouteResponse> routes;
  private long estimatedElapsedTime;
  private double estimatedDistance;
  private LocalDateTime shippingStartDate;
  private LocalDateTime shippingEndDate;
  private boolean isDelete = false;

  @Builder
  private DeliveryResponse(UUID deliveryId, UUID orderId, UUID departureHubId, UUID arrivalHubId,
      DeliveryState state, String shippingAddress, UUID shippingManagerId,
      String shippingManagerSlackId, List<DeliveryRouteResponse> routes, long estimatedElapsedTime,
      double estimatedDistance, LocalDateTime shippingStartDate, LocalDateTime shippingEndDate,
      boolean isDelete) {
    this.deliveryId = deliveryId;
    this.orderId = orderId;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.state = state;
    this.shippingAddress = shippingAddress;
    this.shippingManagerId = shippingManagerId;
    this.shippingManagerSlackId = shippingManagerSlackId;
    this.routes = routes;
    this.estimatedElapsedTime = estimatedElapsedTime;
    this.estimatedDistance = estimatedDistance;
    this.shippingStartDate = shippingStartDate;
    this.shippingEndDate = shippingEndDate;
    this.isDelete = isDelete;
  }

  public static DeliveryResponse fromEntity(Delivery delivery){
    return DeliveryResponse.builder()
        .deliveryId(delivery.getDeliveryId())
        .orderId(delivery.getOrderId())
        .state(delivery.getDeliveryState())
        .departureHubId(delivery.getDepartureHubId())
        .arrivalHubId(delivery.getArrivalHubId())
        .shippingAddress(delivery.getShippingAddress())
        .shippingManagerId(delivery.getShippingManagerId())
        .shippingManagerSlackId(delivery.getShippingManagerSlackId())
        .routes(
            delivery.getRoutes().stream()
                .map(DeliveryRouteResponse::fromEntity)
                .collect(Collectors.toList()))
        .estimatedDistance(delivery.getEstimatedDistance())
        .estimatedElapsedTime(delivery.getEstimatedElapsedTime())
        .shippingStartDate(delivery.getShippingStartDate())
        .shippingEndDate(delivery.getShippingEndDate())
        .build();
  }


}
