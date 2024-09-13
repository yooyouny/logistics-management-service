package com.sparta.company.infrastructure.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub")
public interface HubClient {

  @GetMapping("/internal/hubs/{hubId}")
  ResponseEntity<Void> checkHubExists(@PathVariable("hubId") UUID hubId);

}
