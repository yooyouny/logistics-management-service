package com.sparta.delivery.presentation.controller;

import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.delivery.application.DeliveryFacadeService;
import com.sparta.delivery.application.DeliveryService;
import com.sparta.delivery.presentation.dto.DeliveryResponse;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
@Validated
public class DeliveryController {
  private final DeliveryFacadeService deliveryFacadeService;
  private final DeliveryService deliveryService;

  @PostMapping("/{deliveryId}/requested")
  public ResponseEntity<SuccessResponseBody<DeliveryResponse>> requestDeliveryAndCreateRoute(
      @PathVariable("deliveryId") UUID deliveryId) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new SuccessResponseBody<>(
                deliveryFacadeService.requestDeliveryAndCreateRoute(deliveryId)));
  }

  @GetMapping("/{deliveryId}")
  public SuccessResponseBody<DeliveryResponse> get(
      @NotNull @PathVariable("deliveryId") UUID deliveryId) {
    return new SuccessResponseBody<>(deliveryService.get(deliveryId));
  }
}
