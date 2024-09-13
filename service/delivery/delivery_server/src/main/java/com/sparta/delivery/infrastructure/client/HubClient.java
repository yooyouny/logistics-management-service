package com.sparta.delivery.infrastructure.client;

import com.sparta.hub.dto.InterHubResponse;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "hub-service")
public interface HubClient {
  @GetMapping("/internal/interhubs")
  Optional<InterHubResponse> getDeliveryRoutes(
      @RequestParam("departureHubId") UUID departureHubId,
      @RequestParam("arrivalHubId") UUID arrivalHubId
  );
}
