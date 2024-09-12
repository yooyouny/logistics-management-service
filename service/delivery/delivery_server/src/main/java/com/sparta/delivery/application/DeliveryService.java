package com.sparta.delivery.application;

import com.sparta.delivery.infrastructure.repository.DeliveryRepository;
import com.sparta.delivery.infrastructure.repository.DeliveryRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {
  private final DeliveryRepository deliveryRepository;
  private final DeliveryRouteRepository deliveryRouteRepository;

  //  public DeliveryDto create(UUID shippingManagerId, String shippingManagerSlackId, String
  // shippingAddress, Order order, List<InterHubDto> interHubs){
  // UUID departureHubId = interHubs.get(0).getHubId();
  // UUID arrivalHubId = interHubs.get(interHubs.size() - 1).getHubId;
  //    Delivery delivery = Delivery.builder()
  //        .departureHubId(departureHubId)
  //        .arrivalHubId(arrivalHubId)
  //        .order(order)
  //        .shippingManagerId(shippingManagerId)
  //        .shippingManagerSlackId(shippingManagerSlackId)
  //        .shippingAddress(shippingAddress)
  //        .build();
  //    deliveryRepository.save(delivery);
  //    List<DeliveryRoute> routes = interHubs.stream()
  //        .map(interHub -> DeliveryRoute.builder()
  //            .departureHubId(interHub.getDepartureHubId)
  //            .arrivalHubId(interHub.getArrivalHubId)
  //            .delivery(delivery)
  //            .expectedElapsedTime(interHub.getElapsedTime)
  //            .expectedDistance(interHub.getDistance)
  //            .sequence())
  //        .collect(Collectors.toList());
  //    deliveryRouteRepository.saveAll(routes);
  //    delivery.setDeliveryRoutes(routes);

  //    return DeliveryDto.
  // }
}
