package com.sparta.company.infrastructure.client;


import com.sparta.hub.dto.HubResponse;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub")
public interface HubClient {

  @GetMapping("/internal/hubs/{hubId}/exists")
  boolean checkHubExists(@PathVariable("hubId") UUID hubId);

  // company를 관리하는 hubId로 허브 찾기
  @GetMapping("/internal/hubs/{hubId}")
  Optional<HubResponse> getHubByCompany(@PathVariable("hubId") UUID hubId);
}
