package com.sparta.company.infrastructure.client;

import com.sparta.hub.dto.HubResponse;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub")
public interface HubClient {

  @GetMapping("/internal/hubs/{hubId}")
  boolean checkHubExists(@PathVariable("hubId") UUID hubId);

  @GetMapping("/internal/hubs/{hubId}/manager")
  Optional<HubResponse> getHubByCompany(@PathVariable("hubId") UUID hubId);

}
