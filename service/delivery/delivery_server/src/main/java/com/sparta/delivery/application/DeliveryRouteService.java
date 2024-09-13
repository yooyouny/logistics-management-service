package com.sparta.delivery.application;

import com.sparta.delivery.domain.model.Delivery;
import com.sparta.delivery.domain.model.DeliveryRoute;
import com.sparta.delivery.infrastructure.repository.DeliveryRouteRepository;
import com.sparta.hub.dto.InterHubStopResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryRouteService {
  private final DeliveryRouteRepository deliveryRouteRepository;

  public void createDeliveryRoute(Delivery delivery, List<InterHubStopResponse> interHubs) {
    List<DeliveryRoute> routes =
        interHubs.stream()
            .map(
                interHub ->
                    DeliveryRoute.builder()
                        .delivery(delivery)
                        .sequence(interHub.getSequence())
                        .departureHubId(interHub.getDepartureHubId())
                        .arrivalHubId(interHub.getArrivalHubId())
                        .estimatedDistance(interHub.getDistance())
                        .estimatedElapsedTime(interHub.getElapsedTime())
                        .build())
            .collect(Collectors.toList());
    deliveryRouteRepository.saveAll(routes);
    delivery.setDeliveryRoutes(routes);
  }
}
