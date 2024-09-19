package com.sparta.delivery.presentation.controller;

import com.sparta.delivery.application.DeliveryFacadeService;
import com.sparta.delivery.dto.DeliveryDto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/deliveries")
@RequiredArgsConstructor
@Validated
public class DeliveryInternalController {
  public final DeliveryFacadeService deliveryFacadeService;

  @GetMapping
  public List<DeliveryDto> getDeliveriesByShippingManager(
      @RequestParam("shippingManagerId") @NotNull UUID shippingManagerId,
      @RequestParam("shippingStartDate") @NotNull LocalDateTime shippingStartDate) {
    return deliveryFacadeService.findDeliveriesByShippingManagerId(
        shippingManagerId, shippingStartDate);
  }
}
