package com.sparta.delivery.application;

import com.sparta.delivery.domain.model.Delivery;
import com.sparta.delivery.domain.model.DeliveryRoute;
import com.sparta.delivery.dto.DeliveryDto;
import com.sparta.delivery.dto.DeliveryRouteDto;
import java.util.stream.Collectors;

public class DeliveryMapper {
  public static DeliveryDto toDeliveryDto(Delivery delivery) {
    return DeliveryDto.builder()
        .deliveryId(delivery.getDeliveryId())
        .departureHubId(delivery.getDepartureHubId())
        .arrivalHubId(delivery.getArrivalHubId())
        .deliveryState(delivery.getDeliveryState().name())
        .shippingManagerId(delivery.getShippingManagerId())
        .shippingManagerSlackId(delivery.getShippingManagerSlackId())
        .shippingAddress(delivery.getShippingAddress())
        .shippingStartDate(delivery.getShippingStartDate())
        .shippingEndDate(delivery.getShippingEndDate())
        .estimatedDistance(delivery.getEstimatedDistance())
        .estimatedElapsedTime(delivery.getEstimatedElapsedTime())
        .routes(
            delivery.getRoutes().stream()
                .map(DeliveryMapper::toDeliveryRouteDto)
                .collect(Collectors.toList()))
        .build();
  }

  public static DeliveryRouteDto toDeliveryRouteDto(DeliveryRoute deliveryRoute) {
    return DeliveryRouteDto.builder()
        .deliveryRouteId(deliveryRoute.getDeliveryRouteId())
        .sequence(deliveryRoute.getSequence())
        .departureHubId(deliveryRoute.getDepartureHubId())
        .arrivalHubId(deliveryRoute.getArrivalHubId())
        .routeState(deliveryRoute.getRouteState().name())
        .estimatedDistance(deliveryRoute.getEstimatedDistance())
        .estimatedElapsedTime(deliveryRoute.getEstimatedElapsedTime())
        .realElapsedTime(deliveryRoute.getRealElapsedTime())
        .build();
  }
}
