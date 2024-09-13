package com.sparta.delivery.presentation;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.delivery.application.DeliveryFacadeService;
import com.sparta.delivery.presentation.dto.DeliveryResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {
  private final DeliveryFacadeService deliveryFacadeService;

  @PostMapping("/{deliveryId}/requested")
  public ResponseBody<DeliveryResponse> requestDeliveryAndCreateRoute(
      @PathVariable("deliveryId") UUID deliveryId) {
    return new SuccessResponseBody<>(
        deliveryFacadeService.requestDeliveryAndCreateRoute(deliveryId));
  }
}
