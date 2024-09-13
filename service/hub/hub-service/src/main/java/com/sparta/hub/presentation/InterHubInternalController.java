package com.sparta.hub.presentation;

import com.sparta.hub.application.dto.hub.HubResponse;
import com.sparta.hub.application.mapper.HubMapper;
import com.sparta.hub.application.service.HubService;
import com.sparta.hub.application.service.InterHubService;
import com.sparta.hub.domain.Hub;
import com.sparta.hub.dto.InterHubResponse;
import com.sparta.hub.infrastructure.feign.CompanyClient;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/interhubs")
@RequiredArgsConstructor
public class InterHubInternalController {

  private final InterHubService interHubService;
  private final HubService hubService;

  @GetMapping
  public Optional<InterHubResponse> getDeliveryRoutes(
      @RequestParam("departureHubId") UUID departureHubId,
      @RequestParam("arrivalHubId") UUID arrivalHubId
  ){
    InterHubResponse interHubResponse = interHubService.findInterHubByDpHubAndAvHub(
        departureHubId, arrivalHubId);
    return Optional.ofNullable(interHubResponse);
  }
}
